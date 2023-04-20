package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.CommonMappers.Companion.getRefType
import com.codebootup.codegenerator.CommonMappers.Companion.mapType
import com.codebootup.codegenerator.model.DataClass
import com.codebootup.codegenerator.model.DataInterface
import com.codebootup.codegenerator.model.DataInterfaceSubType
import com.codebootup.codegenerator.model.DataPrimitiveClass
import com.codebootup.codegenerator.model.DataSubClass
import com.codebootup.codegenerator.model.EnumClass
import com.codebootup.codegenerator.model.EnumValue
import com.codebootup.codegenerator.model.ModelType
import com.codebootup.codegenerator.model.Property
import com.codebootup.codegenerator.model.SchemaObject
import org.openapi4j.parser.model.v3.OpenApi3
import org.openapi4j.parser.model.v3.Schema

class KotlinSpringSchemaObjectBuilder(
    private val modelPackage: String,
) {

    fun build(inputModel: OpenApi3): List<SchemaObject> {
        val dataInterfaces = dataInterfaces(inputModel)
        return dataClasses(inputModel) +
            dataInterfaces +
            dataSubClasses(inputModel, dataInterfaces) +
            enumClasses(inputModel) +
            dataPrimitiveClasses(inputModel)
    }

    private fun dataClasses(inputModel: OpenApi3): List<SchemaObject> {
        return inputModel.components.schemas.entries
            .asSequence()
            .filter { it.value != null }
            .filter { it.value.type == "object" }
            .filter { it.value.discriminator == null }
            .filter { it.value.allOfSchemas?.none { p -> p.ref != null } ?: true }
            .filter { it.value.enums?.isEmpty() ?: true }
            .map {
                DataClass(
                    classname = it.key,
                    properties = properties(it.value),
                )
            }
            .toList()
    }

    private fun dataInterfaces(inputModel: OpenApi3): List<DataInterface> {
        return inputModel.components.schemas.entries
            .filter { it.value != null }
            .filter { it.value.discriminator != null }
            .map {
                DataInterface(
                    classname = it.key,
                    properties = properties(it.value).filter { p -> p.originalFieldName != it.value.discriminator.propertyName },
                    discriminator = it.value.discriminator.propertyName,
                    subTypes = it.value.discriminator.mapping.entries.map { m ->
                        DataInterfaceSubType(
                            discriminatorValue = m.key,
                            classname = m.value.substring(m.value.lastIndexOf('/') + 1),
                        )
                    },
                )
            }
    }

    private fun dataSubClasses(inputModel: OpenApi3, dataInterfaces: List<DataInterface>): List<DataSubClass> {
        return inputModel.components.schemas.entries
            .asSequence()
            .filter { it.value != null }
            .filter { it.value.discriminator == null }
            .filter { it.value.allOfSchemas != null }
            .filter { it.value.allOfSchemas.any { p -> p.ref != null } }
            .map {
                val (updatedSubClassProperties, updatedDataInterface) = findDataInterface(dataInterfaces, it.value)
                    .overridePropertiesWithSubClass(properties(it.value))
                DataSubClass(
                    classname = it.key,
                    properties = updatedSubClassProperties,
                    dataInterface = updatedDataInterface,
                )
            }
            .toList()
    }

    private fun enumClasses(inputModel: OpenApi3): List<EnumClass> {
        return inputModel.components.schemas.entries
            .filter { it.value != null }
            .filter { it.value.enums?.isNotEmpty() ?: false }
            .map {
                EnumClass(
                    classname = it.key,
                    type = mapType(type = it.value.type, required = true).type,
                    values = it.value.enums.map { e ->
                        EnumValue(
                            originalEnumValue = e.toString(),
                        )
                    },
                )
            }
    }

    private fun dataPrimitiveClasses(inputModel: OpenApi3): List<DataPrimitiveClass> {
        return inputModel.components.schemas.entries
            .asSequence()
            .filter { it.value != null }
            .filter { it.value.type != "object" }
            .filter { it.value.enums?.isEmpty() ?: true }
            .map {
                DataPrimitiveClass(
                    classname = it.key,
                    underlyingType = mapType(type = it.value.type, format = it.value.format, required = true),
                    typePackage = modelPackage,
                )
            }.toList()
    }

    private fun findDataInterface(
        dataInterfaces: List<DataInterface>,
        subClassComponent: Schema,
    ): DataInterface {
        val interfaceRef = subClassComponent.allOfSchemas.first { p -> p.ref != null }
        val interfaceClassname = getRefType(interfaceRef)
        return dataInterfaces.first { it.classname == interfaceClassname }
    }

    private fun DataInterface.overridePropertiesWithSubClass(subClassProperties: List<Property>): Pair<List<Property>, DataInterface> {
        val mutableSubClassProperties = subClassProperties.toMutableList()
        val updatedDataInterface = this.copy(
            properties = properties.map { p ->
                val matched =
                    mutableSubClassProperties.firstOrNull { scp -> scp.originalFieldName == p.originalFieldName }
                if (matched != null) {
                    mutableSubClassProperties.remove(matched)
                    p.copy(
                        type = p.type.copy(
                            type = matched.type.type,
                            typePackage = matched.type.typePackage,
                            required = p.type.required || matched.type.required,
                        ),
                    )
                } else {
                    p
                }
            },
        )
        return mutableSubClassProperties.toList() to updatedDataInterface
    }

    private fun properties(schema: Schema): List<Property> {
        val requiredFields = schema.requiredFields ?: listOf()
        val primitiveProperties = primitiveProperties(schema, requiredFields)
        val references = references(schema, requiredFields)
        val oneOfReferences = oneOfReferences(schema, requiredFields)

        return primitiveProperties + references + oneOfReferences
    }

    private fun primitiveProperties(schema: Schema, requiredFields: List<String>): List<Property> {
        return schema.properties?.entries
            ?.filter { it.value.type != null }
            ?.filter { it.value.type != "object" }
            ?.map { p ->
                val required = requiredFields.firstOrNull { it == p.key } != null
                Property(
                    originalFieldName = p.key,
                    type = mapType(type = p.value.type, format = p.value.format, required = required),
                )
            } ?: listOf()
    }

    private fun references(schema: Schema, requiredFields: List<String>): List<Property> {
        return schema.properties?.entries
            ?.filter { it.value.ref != null }
            ?.map {
                val required = requiredFields.contains(it.key)
                val refType = getRefType(it.value)

                Property(
                    originalFieldName = it.key,
                    type = ModelType(
                        type = refType,
                        typePackage = modelPackage,
                        required = required,
                    ),
                )
            } ?: listOf()
    }

    private fun oneOfReferences(schema: Schema, requiredFields: List<String>): List<Property> {
        val oneOfSchemaRefs =
            schema.properties
                ?.entries
                ?.filter { it.value.oneOfSchemas?.any { o -> o.ref != null } ?: false }
                ?.map { it.key to it.value.oneOfSchemas }
                ?: listOf()

        val objectProperties =
            oneOfSchemaRefs
                .filter { it.second.size == 1 }
                .map {
                    val required = requiredFields.contains(it.first)
                    val refType = getRefType(it.second.first())

                    Property(
                        originalFieldName = it.first,
                        type = ModelType(
                            type = refType,
                            typePackage = modelPackage,
                            required = required,
                        ),
                    )
                }

        val numberOneOfSchemasWithMultipleReferences = oneOfSchemaRefs.count { it.second.size > 1 }
        if (numberOneOfSchemasWithMultipleReferences > 0) {
            throw RuntimeException("Multiple oneOf references is currently not supported")
        }

        return objectProperties
    }
}
