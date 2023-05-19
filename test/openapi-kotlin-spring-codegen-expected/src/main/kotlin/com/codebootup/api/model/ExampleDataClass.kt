package com.codebootup.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ExampleDataClass(
    @get:JsonProperty("exampleString", required = true) val exampleString: String,
)
