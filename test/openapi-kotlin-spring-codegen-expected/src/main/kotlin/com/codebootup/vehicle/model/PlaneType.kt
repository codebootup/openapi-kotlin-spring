package com.codebootup.vehicle.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class PlaneType {

    @JsonProperty("Prop")
    Prop,

    @JsonProperty("Jet")
    Jet,
}