package com.codebootup.codegenerator.model

interface Operation {
    val path: String
    val functionName: String
    val responseType: ModelType
    val pathVariables: List<Property>
}
