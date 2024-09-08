package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.connector.smartthings.notifier.SmartThingsTokenService
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.Room
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Test class for [DiscoveryRequestHandler].
 *
 * @author Alex Mikhalochkin
 */
class DiscoveryRequestHandlerTest : BaseRequestHandlerTest<DiscoveryRequestHandler>() {

    @MockK
    lateinit var tokenService: SmartThingsTokenService

    @Test
    fun testInvoke() {
        val device = Device(
            deviceId,
            Room("room"),
            DeviceName("device"),
            "description",
            emptyList()
        )
        every { smartHomeService.getAllDevices() } returns listOf(device)
        val smartThingsDevice = SmartThingsDevice()
        every {
            conversionService.convert(
                device,
                SmartThingsDevice::class.java
            )
        } returns smartThingsDevice
        every { tokenService.isGrantCallbackAccessRequired() } returns false
        val response = executeRequest("discoveryRequest")

        verifyHeaders(response.body, "discoveryResponse")
        assertSame(smartThingsDevice, response.body?.devices!![0])
        assertSame(HttpStatus.OK, response.statusCode)
        verifySequence {
            smartHomeService.getAllDevices()
            conversionService.convert(device, SmartThingsDevice::class.java)
        }
    }
}
