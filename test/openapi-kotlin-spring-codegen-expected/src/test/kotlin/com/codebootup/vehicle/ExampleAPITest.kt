package com.codebootup.vehicle

import com.codebootup.api.model.ExampleDataInterface
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ExampleAPITest {

    @Test
    fun `Can get list of examples no auth`(){
        RestTemplate().exchange(
            "http://localhost:8080/api/v1/examples",
            HttpMethod.GET,
            HttpEntity.EMPTY,
            object : ParameterizedTypeReference<List<ExampleDataInterface>>(){}
        )
    }
    @Test
    fun `Can get list of examples`(){
        val headers = HttpHeaders()
        headers.setBasicAuth("u", "p")
        val entity = HttpEntity<Any>(headers)

        RestTemplate().exchange(
            "http://localhost:8080/api/v1/examples",
            HttpMethod.GET,
            entity,
            object : ParameterizedTypeReference<List<ExampleDataInterface>>(){}
        )
    }
}