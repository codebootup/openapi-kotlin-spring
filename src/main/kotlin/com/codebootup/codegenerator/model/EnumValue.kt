package com.codebootup.codegenerator.model

import org.apache.commons.text.CaseUtils

data class EnumValue(
    val originalEnumValue: String,
    val camelCaseEnumValue: String = if(originalEnumValue.contains("_")) CaseUtils.toCamelCase(originalEnumValue, true, '_') else originalEnumValue,
)
