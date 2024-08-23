package com.am.jarvis

import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.comparator.CustomComparator
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

/**
 * Verifies SmartThings REST endpoints.
 *
 * @author Alex Mikhalochkin
 */
@Disabled("In memory repository")
internal class SmartThingsRestTest : BaseRestTest() {

    @Value("\${yandex.skill.id}")
    private lateinit var yandexSkillId: String
    @Value("\${yandex.token}")
    private lateinit var yandexToken: String

    @Test
    fun testDiscovery() {
        testSmartThings("smartthings/discovery_request.json", "smartthings/discovery_response.json")
    }

    @Test
    fun testStateRefresh() {
        testSmartThings("smartthings/state_refresh_request.json", "smartthings/state_refresh_response.json")
    }

    @Test
    fun testCommand() {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()))
        testSmartThings("smartthings/command_request.json", "smartthings/command_response.json")
        verify(mqttClient).publish("megad/14/cmd", "7:1".toByteArray(), 0, true)
        assertEquals(1, mockWebServer.requestCount)
        val request = mockWebServer.takeRequest()
        assertEquals("POST", request.method)
        assertEquals("/api/v1/skills/$yandexSkillId/callback/state", request.path)
        assertEquals("OAuth $yandexToken", request.headers["Authorization"])
        val comparator = CustomComparator(JSONCompareMode.STRICT, Customization("ts") { _, _ -> true })
        JSONAssert.assertEquals(getPayloadFromFile("notification/yandex/request.json"), request.body.readUtf8(), comparator)
    }

    @Test
    fun testGrantCallbackAccess() {
        testSmartThings(
            "smartthings/grant_callback_access_request.json",
            "smartthings/grant_callback_access_response.json"
        )
    }

    private fun testSmartThings(requestPayloadPath: String, expectedResponsePath: String) {
        val requestBuilder = post("/smartthings")
            .header("Content-Type", "application/json")
            .content(getPayloadFromFile(requestPayloadPath))
        performRequest(requestBuilder)
            .andExpect(content().json(getPayloadFromFile(expectedResponsePath), true))
    }
}
