package com.codebootup.api

import com.codebootup.api.model.ExampleDataClass
import com.codebootup.api.model.ExampleDataInterface
import com.codebootup.api.model.ExampleDataSubClass1
import com.codebootup.api.model.ExamplePrimitiveClass
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.time.LocalDate
import java.time.LocalDateTime

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
    fun `Can get list of examples with auth`() {
        val headers = HttpHeaders()
        headers.setBasicAuth(user, password)
        val entity = HttpEntity<Any>(headers)

        RestTemplate().exchange<List<ExampleDataInterface>>(
            url = "http://localhost:8080/api/v1/examples",
            method = HttpMethod.GET,
            requestEntity = entity
        )
    }

    @Test
    fun `Can post an example with auth`() {
        val headers = HttpHeaders()
        headers.setBasicAuth(user, password)
        val entity = HttpEntity<Any>(
            ExampleDataSubClass1(
                location = ExamplePrimitiveClass("example primitive"),
                exampleBoolean = true,
                exampleDate = LocalDate.MAX,
                exampleDataClass = ExampleDataClass(exampleString = "example string"),
                exampleString = "example string",
                exampleInteger = 3,
                exampleDateTime = LocalDateTime.MAX,
                exampleInteger64 = 64L,
                exampleNumber = 3,
                id = 3
            ), headers
        )

        RestTemplate().exchange<List<ExampleDataInterface>>(
            url = "http://localhost:8080/api/v1/examples",
            method = HttpMethod.POST,
            requestEntity = entity
        )
    }

    @Test
    fun `Throws 401 when trying to post an example`() {
        val headers = HttpHeaders()
        val entity = HttpEntity<Any>(
            ExampleDataSubClass1(
                location = ExamplePrimitiveClass("example primitive"),
                exampleBoolean = true,
                exampleDate = LocalDate.MAX,
                exampleDataClass = ExampleDataClass(exampleString = "example string"),
                exampleString = "example string",
                exampleInteger = 3,
                exampleDateTime = LocalDateTime.MAX,
                exampleInteger64 = 64L,
                exampleNumber = 3,
                id = 3
            ), headers
        )

        assertThatThrownBy {
            RestTemplate().exchange<List<ExampleDataInterface>>(
                url = "http://localhost:8080/api/v1/examples",
                method = HttpMethod.POST,
                requestEntity = entity
            )
        }.isInstanceOf(HttpClientErrorException.Unauthorized::class.java)
    }
}