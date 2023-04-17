package com.codebootup.codegenerator

import com.codebootup.codegenerator.model.ModelType
import org.openapi4j.parser.model.v3.Schema

class CommonMappers {
    companion object {
        fun getRefType(schema: Schema): String =
            schema.ref.substring(schema.ref.lastIndexOf("/") + 1)

        fun mapType(type: String, format: String? = null, required: Boolean): ModelType {
            return when {
                type == "string" && format == "date" -> ModelType(type = "LocalDate", typePackage = "java.time", required = required)
                type == "string" && format == "date-time" -> ModelType(type = "LocalDateTime", typePackage = "java.time", required = required)
                type == "string" -> ModelType(type = "String", typePackage = "kotlin", required = required)

                type == "integer" && format == "int32" -> ModelType(type = "Int", typePackage = "kotlin", required = required)
                type == "integer" && format == "int64" -> ModelType(type = "Long", typePackage = "kotlin", required = required)
                type == "integer" -> ModelType(type = "Int", typePackage = "kotlin", required = required)

                type == "boolean" -> ModelType(type = "Boolean", typePackage = "kotlin", required = required)

                type == "number" && format == "float" -> ModelType(type = "Float", typePackage = "kotlin", required = required)
                type == "number" && format == "double" -> ModelType(type = "Double", typePackage = "kotlin", required = required)
                type == "number" -> ModelType(type = "Number", typePackage = "kotlin", required = required)

                else -> throw RuntimeException("Unknown type $type and format $format")
            }
        }
    }
}
