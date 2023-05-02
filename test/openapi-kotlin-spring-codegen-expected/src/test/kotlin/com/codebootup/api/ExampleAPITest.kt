package com.codebootup.api

import com.codebootup.api.model.ExampleDataInterface
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ExampleAPITest(
    @Value("\${spring.security.user.name}") val user: String,
    @Value("\${spring.security.user.password}") val password: String,
) {

    @Test
    fun `Can get list of examples no auth`() {
        RestTemplate().exchange<List<ExampleDataInterface>>(
            url = "http://localhost:8080/api/v1/examples",
            method = HttpMethod.GET,
            requestEntity = HttpEntity.EMPTY
        )
    }

    @Test
    fun `Can get list of examples`() {
        val headers = HttpHeaders()
        headers.setBasicAuth(user, password)
        val entity = HttpEntity<Any>(headers)

        RestTemplate().exchange<List<ExampleDataInterface>>(
            url = "http://localhost:8080/api/v1/examples",
            method = HttpMethod.GET,
            requestEntity = entity
        )
    }
}