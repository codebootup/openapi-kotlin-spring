package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.CommonMappers.Companion.getRefType
import com.codebootup.codegenerator.CommonMappers.Companion.mapType
import com.codebootup.codegenerator.model.GetOperation
import com.codebootup.codegenerator.model.ModelType
import com.codebootup.codegenerator.model.Operation
import com.codebootup.codegenerator.model.PostOperation
import com.codebootup.codegenerator.model.Property
import org.apache.commons.text.CaseUtils
import org.openapi4j.parser.model.v3.OpenApi3

class KotlinSpringOperationsBuilder(
    private val modelPackage: String,
) {
    fun build(inputModel: OpenApi3): List<Operation> {
        return getOperations(inputModel) + postOperations(inputModel)
    }

    private fun getOperations(inputModel: OpenApi3): List<GetOperation> {
        return inputModel
            .paths
            .entries
            .toList()
            .filter { it.value.get != null }
            .map {
                GetOperation(
                    path = it.key,
                    functionName = CaseUtils.toCamelCase(it.value.get.operationId, false, '-'),
                    responseType = getResponseType(it.value.get),
                    pathVariables = pathVariables(it.value.get),
                )
            }
    }

    private fun postOperations(inputModel: OpenApi3): List<PostOperation> {
        return inputModel
            .paths
            .entries
            .filter { it.value.post != null }
            .map {
                PostOperation(
                    path = it.key,
                    functionName = CaseUtils.toCamelCase(it.value.post.operationId, false, '-'),
                    responseType = getResponseType(it.value.post),
                    pathVariables = pathVariables(it.value.post),
                    requestBody = getRequestBody(it.value.post),
                )
            }
    }

    private fun getResponseType(operation: org.openapi4j.parser.model.v3.Operation): ModelType {
        val jsonResponse2XXSchema = operation
            .responses
            .entries
            .first { it.key.startsWith("2") }
            .value
            .contentMediaTypes
            .entries
            .first { it.key == "application/json" }
            .value
            .schema

        return when (jsonResponse2XXSchema.type) {
            "array" -> ModelType(
                type = getRefType(jsonResponse2XXSchema.itemsSchema),
                typePackage = modelPackage,
                collection = "List",
                required = true,
            )

            null -> ModelType(
                type = getRefType(jsonResponse2XXSchema),
                typePackage = modelPackage,
                required = true,
            )

            else -> throw RuntimeException("Unknown type ${jsonResponse2XXSchema.type}")
        }
    }

    private fun pathVariables(operation: org.openapi4j.parser.model.v3.Operation): List<Property> {
        return operation.parameters?.filter { it.`in` == "path" }?.map {
            Property(
                originalFieldName = it.name,
                type = mapType(type = it.schema.type, format = it.schema.format, required = it.required),
            )
        }?.toList() ?: listOf()
    }

    private fun getRequestBody(operation: org.openapi4j.parser.model.v3.Operation): Property {
        val requestSchema = operation
            .requestBody
            .contentMediaTypes
            .entries
            .first { it.key == "application/json" }
            .value
            .schema

        val refType = getRefType(requestSchema)

        return Property(
            originalFieldName = refType,
            type = ModelType(
                type = refType,
                typePackage = modelPackage,
                required = true,
            ),
        )
    }
}
