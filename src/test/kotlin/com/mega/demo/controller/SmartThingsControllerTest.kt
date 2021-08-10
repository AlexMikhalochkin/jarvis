package com.mega.demo.controller

import com.mega.demo.controller.model.smartthings.Authentication
import com.mega.demo.controller.model.smartthings.Command
import com.mega.demo.controller.model.smartthings.Headers
import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.controller.model.smartthings.SmartThingsRequest
import com.mega.demo.generateUuid
import com.mega.demo.service.api.SmartThingsService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class SmartThingsControllerTest {

    private val deviceId = generateUuid()

    private lateinit var controller: SmartThingsController
    private lateinit var service: SmartThingsService

    @BeforeEach
    fun init() {
        service = mock()
        controller = SmartThingsController(service)
    }

    @Test
    fun testHandleDiscoveryRequest() {
        controller.handle(createRequest("discoveryRequest"))
    }

    @Test
    fun testHandleStateRefreshRequest() {
        controller.handle(createRequest("stateRefreshRequest"))
    }

    @Test
    fun testHandleCommandRequest() {
        controller.handle(createRequest("commandRequest"))
    }

    private fun createRequest(interactionType: String): SmartThingsRequest {
        val command = Command(
            "st.switch",
            "on"
        )
        val devices = listOf(SmartThingsDevice(deviceId, commands = listOf(command)))
        return SmartThingsRequest(
            Headers(
                interactionType,
                generateUuid()
            ),
            devices = devices,
            authentication = Authentication(generateUuid())
        )
    }
}
