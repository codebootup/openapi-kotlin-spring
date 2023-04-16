package com.codebootup.codegenerator.model

import org.apache.commons.text.CaseUtils

data class Property(
    val originalFieldName: String,
    val type: ModelType,
    val camelCaseFieldName: String = CaseUtils.toCamelCase(originalFieldName, false, '_'),
)
