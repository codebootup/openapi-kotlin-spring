package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.model.SecuredEndpoint
import com.codebootup.codegenerator.model.SecurityScheme
import org.openapi4j.parser.model.v3.OpenApi3
import org.openapi4j.parser.model.v3.SecurityRequirement

class KotlinSpringSecurityBuilder {
    fun build(inputModel: OpenApi3): List<SecuredEndpoint> {
        val securitySchemes = inputModel.components.securitySchemes.entries
        val basePath = inputModel.servers.first().url

        return inputModel.paths.entries.toList()
            .flatMap {
                when {
                    it.value.get != null && it.value.get.hasSecurityRequirements() ->
                        it.value.get.securityRequirements.map { securityRequirement ->
                            SecurityEndpointRequirement(
                                method = "GET",
                                path = "$basePath${it.key}",
                                securityRequirement = securityRequirement,
                            )
                        }

                    it.value.post != null && it.value.post.hasSecurityRequirements() ->
                        it.value.post.securityRequirements.map { securityRequirement ->
                            SecurityEndpointRequirement(
                                method = "POST",
                                path = "$basePath${it.key}",
                                securityRequirement = securityRequirement,
                            )
                        }

                    else -> listOf()
                }
            }
            .flatMap {
                it.securityRequirement.requirements.entries.toList().map { req ->
                    val matchedSecurityScheme = securitySchemes.firstOrNull { ss -> ss.key == req.key }?.value
                        ?: throw RuntimeException("Unable to find security scheme in open api docs")

                    SecuredEndpoint(
                        method = it.method,
                        path = it.path,
                        scheme = SecurityScheme.valueOf(
                            matchedSecurityScheme.scheme.lowercase().replaceFirstChar { fc -> fc.uppercase() },
                        ),
                    )
                }
            }
    }
}

data class SecurityEndpointRequirement(
    val method: String,
    val path: String,
    val securityRequirement: SecurityRequirement,
)
