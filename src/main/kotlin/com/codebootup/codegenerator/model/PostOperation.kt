package com.codebootup.codegenerator.model

data class PostOperation(
    override val path: String,
    override val functionName: String,
    override val responseType: ModelType,
    override val pathVariables: List<Property>,
    val requestBody: Property,
) : Operation
