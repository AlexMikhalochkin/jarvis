package com.mega.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DemoApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application.properties"])
internal class YandexRestTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var testService: TestService

    private val mapper = ObjectMapper()

    @Test
    fun testHealth() {
        performRequest(head("/yandex/v1.0"))
    }

    @Test
    fun testUnlink() {
        val requestId = generateUuid()
        val expectedResponse = mapper.writeValueAsString(mapOf("request_id" to requestId))
        val requestBuilder = post("/yandex/v1.0/user/unlink")
            .headers(createHeaders(requestId))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testGetDevices() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = testService.getPayloadFromFile("expected.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = get("/yandex/v1.0/user/devices")
            .headers(httpHeaders)
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testGetDevicesStates() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = testService.getPayloadFromFile("get_states_response.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = post("/yandex/v1.0/user/devices/query")
            .headers(httpHeaders)
            .header("Content-Type", "application/json")
            .content(testService.getPayloadFromFile("get_states_request.json"))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testChangeDevicesStates() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = testService.getPayloadFromFile("change_states_response.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = post("/yandex/v1.0/user/devices/action")
            .headers(httpHeaders)
            .header("Content-Type", "application/json")
            .content(testService.getPayloadFromFile("change_states_request.json"))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    private fun performRequest(requestBuilder: MockHttpServletRequestBuilder) =
        mvc.perform(requestBuilder)
            .andExpect(status().isOk)

    private fun createHeaders(requestId: String): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", generateUuid())
        httpHeaders.add("X-Request-Id", requestId)
        return httpHeaders
    }
}
