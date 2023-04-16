package com.codebootup.vehicle.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Car(
    @get:JsonProperty("id", required = true) override val id: Int,
    @get:JsonProperty("model", required = true) override val model: String,
    @get:JsonProperty("wheels", required = true) val wheels: Wheels,
) : Vehicle
