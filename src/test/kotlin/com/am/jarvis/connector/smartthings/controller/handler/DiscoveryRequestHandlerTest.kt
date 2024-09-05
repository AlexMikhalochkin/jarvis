package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.Room
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DiscoveryRequestHandlerTest : BaseRequestHandlerTest<DiscoveryRequestHandler>() {

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
        val body = executeRequest("discoveryRequest")
        verifyHeaders(body, "discoveryResponse")
        assertSame(smartThingsDevice, body.devices!![0])
        verifySequence {
            smartHomeService.getAllDevices()
            conversionService.convert(device, SmartThingsDevice::class.java)
        }
    }
}
