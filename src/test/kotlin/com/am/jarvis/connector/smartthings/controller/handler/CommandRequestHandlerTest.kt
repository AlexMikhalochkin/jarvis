package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class CommandRequestHandlerTest : BaseRequestHandlerTest<CommandRequestHandler>() {

    @Test
    fun testHandleSmartThingsCommand() {
        val deviceState = createDeviceState()
        val smartThingsDevice = SmartThingsDevice(deviceId)
        every {
            conversionService.convert(
                smartThingsDevice,
                DeviceState::class.java
            )
        } returns deviceState
        every { smartHomeService.changeStates(listOf(deviceState), "SMART_THINGS") } returns listOf(deviceState)
        val deviceState1 = com.am.jarvis.controller.generated.model.DeviceState()
        every {
            conversionService.convert(
                deviceState,
                com.am.jarvis.controller.generated.model.DeviceState::class.java
            )
        } returns deviceState1
        val body = executeRequest("commandRequest", listOf(smartThingsDevice))
        verifyHeaders(body, "commandResponse")
        assertSame(deviceState1, body.deviceState!![0])
        verifySequence {
            conversionService.convert(smartThingsDevice, DeviceState::class.java)
            smartHomeService.changeStates(listOf(deviceState), "SMART_THINGS")
            conversionService.convert(deviceState, com.am.jarvis.controller.generated.model.DeviceState::class.java)
        }
    }
}
