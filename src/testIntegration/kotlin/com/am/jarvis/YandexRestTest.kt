package com.am.jarvis

import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

/**
 * Verifies Yandex REST endpoints.
 *
 * @author Alex Mikhalochkin
 */
internal class YandexRestTest : BaseRestTest() {

    @Test
    fun testHealth() {
        performRequest(head("/yandex/v1.0"))
    }

    @Test
    fun testUnlink() {
        val requestId = generateUuid()
        val expectedResponse = convertToJson(mapOf("request_id" to requestId))
        val requestBuilder = post("/yandex/v1.0/user/unlink")
            .headers(createHeaders(requestId))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testGetDevices() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = getPayloadFromFile("expected.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = get("/yandex/v1.0/user/devices")
            .headers(httpHeaders)
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testGetDevicesStates() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = getPayloadFromFile("get_states_response.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = post("/yandex/v1.0/user/devices/query")
            .headers(httpHeaders)
            .header("Content-Type", "application/json")
            .content(getPayloadFromFile("get_states_request.json"))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun testChangeDevicesStates() {
        val requestId = "ff36a3cc-ec34-11e6-b1a0-64510650abcf"
        val expectedResponse = getPayloadFromFile("change_states_response.json")
        val httpHeaders = createHeaders(requestId)
        val requestBuilder = post("/yandex/v1.0/user/devices/action")
            .headers(httpHeaders)
            .header("Content-Type", "application/json")
            .content(getPayloadFromFile("change_states_request.json"))
        performRequest(requestBuilder)
            .andExpect(content().json(expectedResponse, true))
        verify(mqttClient).publish("megad-id/cmd", "7:0".toByteArray(), 0, true)
    }

    private fun createHeaders(requestId: String): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", generateUuid())
        httpHeaders.add("X-Request-Id", requestId)
        return httpHeaders
    }
}
