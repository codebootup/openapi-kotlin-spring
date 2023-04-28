package com.codebootup.codegenerator.model

data class SecuredEndpoints(
    val scheme: SecurityScheme,
    val endpoints: List<SecuredEndpoint>,
)

data class SecuredEndpoint(
    val method: String,
    val path: String,
)

enum class SecurityScheme(
    @Suppress("unused") val springMethod: String,
) {
    Basic("httpBasic"),
}
