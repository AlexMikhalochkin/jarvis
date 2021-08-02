package com.mega.demo.controller

import com.mega.demo.controller.model.smartthings.Authentication
import com.mega.demo.controller.model.smartthings.Command
import com.mega.demo.controller.model.smartthings.Headers
import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.controller.model.smartthings.SmartThingsRequest
import com.mega.demo.model.DeviceStatus
import com.mega.demo.service.impl.smartthings.SmartThingsService
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.core.convert.ConversionService

internal class SmartThingsControllerTest {

    private val deviceId = UUID.randomUUID().toString()

    private lateinit var controller: SmartThingsController
    private lateinit var service: SmartThingsService
    private lateinit var conversionService: ConversionService

    @BeforeEach
    fun init() {
        service = mock()
        conversionService = mock()
        controller = SmartThingsController(service, conversionService)
    }

    @Test
    fun testHandleDiscoveryRequest() {
        controller.handle(createRequest("discoveryRequest"))
    }

    @Test
    fun testHandleStateRefreshRequest() {
        whenever(service.getStatuses(listOf(deviceId))).thenReturn(listOf(DeviceStatus(deviceId, true)))
        controller.handle(createRequest("stateRefreshRequest"))
    }

    @Test
    fun testHandleCommandRequest() {
        controller.handle(createRequest("commandRequest"))
        verify(service).changeStatus(listOf(deviceId), true)
    }

    private fun createRequest(interactionType: String): SmartThingsRequest {
        val command = Command(
            "main",
            "st.switch",
            "on"
        )
        val devices = listOf(SmartThingsDevice(deviceId, commands = listOf(command)))
        return SmartThingsRequest(
            Headers(
                interactionType,
                UUID.randomUUID().toString()
            ),
            Authentication(UUID.randomUUID().toString()),
            devices
        )
    }
}
