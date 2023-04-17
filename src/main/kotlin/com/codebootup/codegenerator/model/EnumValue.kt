package com.codebootup.codegenerator.model

import org.apache.commons.text.CaseUtils

data class EnumValue(
    val originalEnumValue: String,
    val camelCaseEnumValue: String = CaseUtils.toCamelCase(originalEnumValue, true, '_'),
)
