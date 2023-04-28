package com.codebootup.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

data class ExampleDataSubClass2(
    @get:JsonProperty("id", required = true) override val id: Int,
    @get:JsonProperty("exampleString", required = true) override val exampleString: String,
    @get:JsonProperty("exampleBoolean", required = true) override val exampleBoolean: Boolean,
    @get:JsonProperty("exampleNumber", required = true) override val exampleNumber: Number,
    @get:JsonProperty("exampleInteger", required = true) override val exampleInteger: Int,
    @get:JsonProperty("exampleInteger64", required = true) override val exampleInteger64: Long,
    @get:JsonProperty("exampleDate", required = true) override val exampleDate: LocalDate,
    @get:JsonProperty("exampleDateTime", required = true) override val exampleDateTime: LocalDateTime,
    @get:JsonProperty("exampleEnum", required = true) val exampleEnum: ExampleEnum,
) : ExampleDataInterface
