package com.codebootup.vehicle

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class VehicleAPITest {
    @Test
    fun test(){
        RestTemplate().getForEntity(
            "http://localhost:8080/api/v1/vehicles",
            String::class.java
        )
    }
}