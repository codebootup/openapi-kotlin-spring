package com.codebootup.vehicle

import com.codebootup.api.ExampleAPI
import com.codebootup.api.model.*
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
class ExampleAPIImpl : ExampleAPI {

    private val map : MutableMap<Int, ExampleDataInterface> = mutableMapOf(
        Pair(
            1, ExampleDataSubClass1(
                location = ExamplePrimitiveClass("examplePrimative"),
                exampleBoolean = true,
                exampleDate = LocalDate.MAX,
                exampleDataClass = ExampleDataClass(exampleString = "example string"),
                exampleString = "example string",
                exampleInteger = 1,
                exampleDateTime = LocalDateTime.MAX,
                exampleInteger64 = 64L,
                exampleNumber = 2,
                id = 1
            )
        ),
        Pair(
            2, ExampleDataSubClass2(
                exampleBoolean = true,
                exampleDate = LocalDate.MAX,
                exampleString = "example string",
                exampleInteger = 1,
                exampleDateTime = LocalDateTime.MAX,
                exampleInteger64 = 64L,
                exampleNumber = 2,
                exampleEnum = ExampleEnum.EnumValue2,
                id = 2
            )
        )
    )

    override fun exampleGet(): List<ExampleDataInterface> {
        return map.toList().map { it.second }
    }

    override fun exampleGetById(id: Int): ExampleDataInterface {
        return map.toList().filter { it.first == id }.map { it.second }.first()
    }

    override fun examplePost(exampleDataInterface: ExampleDataInterface): List<ExampleDataInterface> {
        map[exampleDataInterface.id] = exampleDataInterface
        return exampleGet()
    }

}