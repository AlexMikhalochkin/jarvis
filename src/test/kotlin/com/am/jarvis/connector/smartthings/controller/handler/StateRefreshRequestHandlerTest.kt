package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class StateRefreshRequestHandlerTest : BaseRequestHandlerTest<StateRefreshRequestHandler>() {

    @Test
    fun testHandleSmartThingsStateRefresh() {
        val deviceState = createDeviceState()
        every { smartHomeService.getDeviceStates(listOf(deviceId)) } returns listOf(deviceState)
        val deviceState1 = com.am.jarvis.controller.generated.model.DeviceState()
        every {
            conversionService.convert(
                deviceState,
                com.am.jarvis.controller.generated.model.DeviceState::class.java
            )
        } returns deviceState1
        val body = executeRequest("stateRefreshRequest", listOf(SmartThingsDevice(deviceId)))
        verifyHeaders(body, "stateRefreshResponse")
        assertSame(deviceState1, body.deviceState!![0])
        verifySequence {
            smartHomeService.getDeviceStates(listOf(deviceId))
            conversionService.convert(deviceState, com.am.jarvis.controller.generated.model.DeviceState::class.java)
        }
    }
}
