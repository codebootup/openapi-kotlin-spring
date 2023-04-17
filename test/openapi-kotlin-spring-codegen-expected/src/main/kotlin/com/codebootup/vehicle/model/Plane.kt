package com.codebootup.vehicle.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

data class Plane(
    @get:JsonProperty("id", required = true) override val id: Int,
    @get:JsonProperty("model", required = true) override val model: String,
    @get:JsonProperty("name", required = true) override val name: String,
    @get:JsonProperty("can_fly", required = false) override val canFly: Boolean?,
    @get:JsonProperty("mileage", required = true) override val mileage: Double,
    @get:JsonProperty("registration_number", required = true) override val registrationNumber: Long,
    @get:JsonProperty("manufactured_date", required = true) override val manufacturedDate: LocalDate,
    @get:JsonProperty("purchased_time", required = true) override val purchasedTime: LocalDateTime,
    @get:JsonProperty("number_of_engines", required = true) val numberOfEngines: Int,
    @get:JsonProperty("airline_name", required = false) val airlineName: String?,
    @get:JsonProperty("plane_type", required = true) val planeType: PlaneType,
    @get:JsonProperty("wheels", required = false) val wheels: Wheels?,
) : Vehicle
