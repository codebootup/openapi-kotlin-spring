package com.codebootup.api.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate
import java.time.LocalDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "subType", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = ExampleDataSubClass1::class, name = "subClass1"),
    JsonSubTypes.Type(value = ExampleDataSubClass2::class, name = "subClass2"),
)
interface ExampleDataInterface {
    val id: Int
    val exampleString: String
    val exampleBoolean: Boolean
    val exampleNumber: Number
    val exampleInteger: Int
    val exampleInteger64: Long
    val exampleDate: LocalDate
    val exampleDateTime: LocalDateTime
}
