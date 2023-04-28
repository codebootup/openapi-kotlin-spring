package com.codebootup.api.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class ExampleEnum {

    @JsonProperty("EnumValue1")
    EnumValue1,

    @JsonProperty("EnumValue2")
    EnumValue2,
}