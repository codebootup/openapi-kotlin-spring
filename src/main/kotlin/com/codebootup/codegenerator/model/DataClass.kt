package com.codebootup.codegenerator.model

data class DataClass(
    override val classname: String,
    val properties: List<Property>,
) : SchemaObject {
    @Suppress("unused")
    val dataImports = properties
        .map { it.type.packageImport }
        .toSet()
        .filter { it.startsWith("java.") }
        .sorted()
}
