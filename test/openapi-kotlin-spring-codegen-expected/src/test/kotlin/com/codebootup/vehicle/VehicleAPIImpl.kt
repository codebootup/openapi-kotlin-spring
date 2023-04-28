package com.codebootup.vehicle

import com.codebootup.vehicle.model.Vehicle
import org.springframework.web.bind.annotation.RestController

@RestController
class VehicleAPIImpl : VehicleAPI {
    override fun getVehicles(): List<Vehicle> {
        TODO("Not yet implemented")
    }

    override fun getVehicleById(id: Int): Vehicle {
        TODO("Not yet implemented")
    }

    override fun postVehicles(vehicle: Vehicle): List<Vehicle> {
        TODO("Not yet implemented")
    }
}