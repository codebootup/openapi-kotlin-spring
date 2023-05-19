package com.codebootup.codegenerator.model

import org.apache.commons.text.CaseUtils

data class Property(
    val originalFieldName: String,
    val type: ModelType,
    val camelCaseFieldName: String = if (originalFieldName.contains("_")) CaseUtils.toCamelCase(originalFieldName, false, '_') else originalFieldName,
)
