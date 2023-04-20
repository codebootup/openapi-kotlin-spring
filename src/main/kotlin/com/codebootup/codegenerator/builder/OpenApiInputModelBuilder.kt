package com.codebootup.codegenerator.builder

import org.openapi4j.parser.OpenApi3Parser
import org.openapi4j.parser.model.v3.OpenApi3
import java.io.File

class OpenApiInputModelBuilder(private val spec: File) {
    fun build(): OpenApi3 {
        return OpenApi3Parser().parse(spec, true)
    }
}
