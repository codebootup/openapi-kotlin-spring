package com.codebootup.codegenerator.model

data class DataInterface(
    override val classname: String,
    val properties: List<Property>,
    val discriminator: String,
    val subTypes: List<DataInterfaceSubType>,
) : SchemaObject {
    @Suppress("unused")
    val dataImports = properties
        .map { it.type.packageImport }
        .toSet()
        .filter { it.startsWith("java.") }
        .sorted()
}
