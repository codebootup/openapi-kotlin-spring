package com.codebootup.codegenerator

import com.codebootup.compare.AssertDirectories
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class GenerateOpenAPIKotlinSpringCodeTest {
    private val sourceDirectory = Path.of("test/openapi-kotlin-spring-codegen-actual/src/main/kotlin")
    private val expectedDirectory = Path.of("test/openapi-kotlin-spring-codegen-expected/src/main/kotlin")

    @Test
    fun `can create api`() {
        generateCode("api")
    }

    private fun generateCode(spec: String) {
        GenerateOpenAPIKotlinSpringCode(
            mapOf(
                "openApiSpecification" to "src/test/resources/$spec.yml",
                "apiPackage" to "com.codebootup.$spec",
                "sourceDirectory" to sourceDirectory.absolutePathString(),
            ),
        )
            .build()
            .render()

        AssertDirectories
            .assertThat(Path.of(sourceDirectory.absolutePathString(), "com/codebootup/$spec"))
            .isEqualTo(Path.of(expectedDirectory.absolutePathString(), "com/codebootup/$spec"))
    }
}
