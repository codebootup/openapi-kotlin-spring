package com.codebootup.vehicle.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Wheels(
    @get:JsonProperty("brand", required = true) val brand: String,
)
