package com.mega.demo

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

/**
 * Verifies SmartThings REST endpoints.
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsRestTest : BaseRestTest() {

    @Test
    fun testDiscovery() {
        testSmartThings("smartthings/discovery_request.json", "smartthings/discovery_response.json")
    }

    @Test
    fun testStateRefresh() {
        testSmartThings("smartthings/state_refresh_request.json", "smartthings/state_refresh_response.json")
    }

    @Test
    @Disabled
    fun testCommand() {
        testSmartThings("smartthings/command_request.json", "smartthings/command_response.json")
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
