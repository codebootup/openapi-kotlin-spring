package com.codebootup.codegenerator.model

data class SecuredEndpoint(
    val method: String,
    val path: String,
    val scheme: SecurityScheme,
)

enum class SecurityScheme {
    Basic,
}
