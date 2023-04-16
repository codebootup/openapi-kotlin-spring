package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.model.DataClass
import com.codebootup.codegenerator.model.DataInterface
import com.codebootup.codegenerator.model.DataInterfaceSubType
import com.codebootup.codegenerator.model.DataSubClass
import com.codebootup.codegenerator.model.ModelType
import com.codebootup.codegenerator.model.Property
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class KotlinSpringSchemaObjectBuilderTest {

    @Test
    fun `can build operations`() {
        val inputModel = OpenApiInputModelBuilder(File("src/test/resources/vehicle.yml")).build()
        val operationList = KotlinSpringSchemaObjectBuilder("com.codebootup.vehicle").build(inputModel)
        println(operationList)
        val dataClass = DataClass(
            classname = "Wheels",
            properties = listOf(
                Property(
                    originalFieldName = "brand",
                    type = ModelType(type = "String", typePackage = "kotlin", required = true)
                )
            )
        )
        val vehicleDataInterface = DataInterface(
            classname = "Vehicle",
            properties = listOf(
                Property(
                    originalFieldName = "id",
                    type = ModelType(type = "Int", typePackage = "kotlin", required = true)
                ),
                Property(
                    originalFieldName = "model",
                    type = ModelType(type = "String", typePackage = "kotlin", required = true)
                )
            ),
            discriminator = "type",
            subTypes = listOf(
                DataInterfaceSubType(classname = "Car", discriminatorValue = "car")
            )
        )
        val dataSubClass = DataSubClass(
            classname = "Car",
            properties = listOf(
                Property(
                    originalFieldName = "wheels",
                    type = ModelType(type = "Wheels", typePackage = "com.codebootup.vehicle", required = true)
                )
            ),
            dataInterface = vehicleDataInterface
        )

        assertEquals(
            expected = listOf(
                dataClass,
                vehicleDataInterface,
                dataSubClass
            ),
            actual = operationList
        )
    }
}