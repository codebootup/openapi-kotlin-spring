package com.codebootup.codegenerator.model

data class GetOperation(
    override val path: String,
    override val functionName: String,
    override val responseType: ModelType,
    override val pathVariables: List<Property>,
) : Operation
