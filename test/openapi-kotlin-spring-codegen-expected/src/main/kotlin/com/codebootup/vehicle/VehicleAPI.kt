package com.codebootup.vehicle

import com.codebootup.vehicle.model.Vehicle
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@RequestMapping("/api/v1")
interface VehicleAPI {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/vehicles"],
        produces = ["application/json"]
    )
    fun getVehicles(): List<Vehicle>

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/vehicles/{id}"],
        produces = ["application/json"]
    )
    fun getVehicleById(@PathVariable("id") id: Int): Vehicle

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/vehicles"],
        produces = ["application/json"]
    )
    fun postVehicles(@RequestBody(required = true) vehicle: Vehicle): List<Vehicle>
}