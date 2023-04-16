package com.codebootup.codegenerator.model

data class ModelType(
    val type: String,
    val typePackage: String,
    val required: Boolean,
    val collection: String? = null,
    val packageImport: String = "$typePackage.$type",
) {
    @Suppress("unused")
    val fullTypeDefinition: String =
        when {
            collection != null && !required -> "$collection<$type>?"
            collection != null && required -> "$collection<$type>"
            !required -> "$type?"
            else -> type
        }
}
