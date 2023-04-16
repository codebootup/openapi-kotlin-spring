package com.codebootup.codegenerator.model

data class DataSubClass(
    override val classname: String,
    val properties: List<Property>,
    val dataInterface: DataInterface,
) : SchemaObject {
    @Suppress("unused")
    val dataImports = (properties.map { it.type.packageImport } + dataInterface.dataImports)
        .toSet()
        .filter { it.startsWith("java.") }
        .sorted()
}
