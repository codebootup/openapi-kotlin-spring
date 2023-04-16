package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.model.GetOperation
import com.codebootup.codegenerator.model.ModelType
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class KotlinSpringOperationsBuilderTest {

    @Test
    fun `can build operations`() {
        val inputModel = OpenApiInputModelBuilder(File("src/test/resources/vehicle.yml")).build()
        val operationList = KotlinSpringOperationsBuilder("com.codebootup.vehicle").build(inputModel)
        assertEquals(
            expected = listOf(
                GetOperation(
                    path = "/vehicles",
                    functionName = "getVehicles",
                    responseType = ModelType(
                        type = "Vehicle",
                        typePackage = "com.codebootup.vehicle",
                        required = true,
                        collection = "List"
                    ),
                    pathVariables = listOf()
                )
            ),
            actual = operationList
        )
    }
}