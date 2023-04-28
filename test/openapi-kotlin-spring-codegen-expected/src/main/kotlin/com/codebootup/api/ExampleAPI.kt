package com.codebootup.api

import com.codebootup.api.model.ExampleDataInterface
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@RequestMapping("/api/v1")
interface ExampleAPI {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/examples"],
        produces = ["application/json"]
    )
    fun exampleGet(): List<ExampleDataInterface>

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/example/{id}"],
        produces = ["application/json"]
    )
    fun exampleGetById(@PathVariable("id") id: Int): ExampleDataInterface

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/examples"],
        produces = ["application/json"]
    )
    fun examplePost(@RequestBody(required = true) exampleDataInterface: ExampleDataInterface): List<ExampleDataInterface>
}
