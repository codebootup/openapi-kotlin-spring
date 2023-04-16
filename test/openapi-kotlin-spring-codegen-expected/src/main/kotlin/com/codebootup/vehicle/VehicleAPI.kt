package com.codebootup.vehicle

import com.codebootup.vehicle.model.Vehicle
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
}