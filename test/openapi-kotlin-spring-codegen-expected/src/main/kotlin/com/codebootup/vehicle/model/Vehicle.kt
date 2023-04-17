package com.codebootup.vehicle.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate
import java.time.LocalDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = Car::class, name = "car"),
)
interface Vehicle {
    val id: Int
    val model: String
    val name: String
    val canFly: Boolean?
    val mileage: Number
    val registrationNumber: Long
    val manufacturedDate: LocalDate
    val purchasedTime: LocalDateTime
}
