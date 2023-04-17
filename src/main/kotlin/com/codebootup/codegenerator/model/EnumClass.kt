package com.codebootup.codegenerator.model

data class EnumClass(
    override val classname: String,
    val type: String,
    val values: List<EnumValue>,
) : SchemaObject
