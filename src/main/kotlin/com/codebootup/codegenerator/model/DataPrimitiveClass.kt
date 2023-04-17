package com.codebootup.codegenerator.model

data class DataPrimitiveClass(
    override val classname: String,
    val underlyingType: ModelType,
    val typePackage: String,
    val packageImport: String = "$typePackage.$classname",
) : SchemaObject
