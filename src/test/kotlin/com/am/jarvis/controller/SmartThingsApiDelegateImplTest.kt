package com.am.jarvis.controller

import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Verification for [SmartThingsApiDelegateImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsApiDelegateImplTest : BaseDelegateTest<SmartThingsApiDelegateImpl>() {

    @Test
    fun testHandleSmartThingsDiscovery() {
        val device = createDevice()
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
        every { smartHomeService.changeStates(listOf(deviceState), Provider.SMART_THINGS) } returns listOf(deviceState)
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
            smartHomeService.changeStates(listOf(deviceState), Provider.SMART_THINGS)
            conversionService.convert(deviceState, com.am.jarvis.controller.generated.model.DeviceState::class.java)
        }
    }

    @Test
    fun testHandleSmartThingsGrantCallbackAccess() {
        val body = executeRequest("grantCallbackAccess")
        verifyHeaders(body, "grantCallbackAccess")
    }

    private fun verifyHeaders(body: SmartThingsResponse?, expectedInteractionType: String) {
        val headers = body!!.headers
        assertEquals(requestId, headers!!.requestId)
        assertEquals(expectedInteractionType, headers.interactionType)
    }

    private fun executeRequest(
        interactionType: String,
        smartThingsDevices: List<SmartThingsDevice>? = null
    ): SmartThingsResponse {
        val smartThingsRequest = SmartThingsRequest(
            Headers(interactionType = interactionType, requestId = requestId),
            Authentication(),
            smartThingsDevices
        )
        val response = delegate.handleSmartThings(smartThingsRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        return response.body!!
    }
}
