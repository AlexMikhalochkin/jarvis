package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Test class for [CommandRequestHandler].
 *
 * @author Alex Mikhalochkin
 */
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

        val response = executeRequest("commandRequest", listOf(smartThingsDevice))

        verifyHeaders(response.body, "commandResponse")
        assertSame(deviceState1, response.body?.deviceState!![0])
        assertSame(HttpStatus.OK, response.statusCode)

        verifySequence {
            conversionService.convert(smartThingsDevice, DeviceState::class.java)
            smartHomeService.changeStates(listOf(deviceState), "SMART_THINGS")
            conversionService.convert(deviceState, com.am.jarvis.controller.generated.model.DeviceState::class.java)
        }
    }
}
