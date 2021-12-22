package com.mega.demo.controller

import com.mega.demo.controller.generated.model.Authentication
import com.mega.demo.controller.generated.model.Headers
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.controller.generated.model.SmartThingsRequest
import com.mega.demo.controller.generated.model.SmartThingsResponse
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
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
        assertSame(smartThingsDevice, body!!.devices!![0])
        verifySequence {
            smartHomeService.getAllDevices()
            conversionService.convert(device, SmartThingsDevice::class.java)
        }
    }

    @Test
    fun testHandleSmartThingsStateRefresh() {
        val id = "id"
        val deviceState = DeviceState("id", 7, true)
        every { smartHomeService.getDeviceStates(listOf(id)) } returns listOf(deviceState)
        val deviceState1 = com.mega.demo.controller.generated.model.DeviceState()
        every {
            conversionService.convert(
                deviceState,
                com.mega.demo.controller.generated.model.DeviceState::class.java
            )
        } returns deviceState1
        val body = executeRequest("stateRefreshRequest", listOf(SmartThingsDevice(id)))
        verifyHeaders(body, "stateRefreshResponse")
        assertSame(deviceState1, body!!.deviceState!![0])
        verifySequence {
            smartHomeService.getDeviceStates(listOf(id))
            conversionService.convert(deviceState, com.mega.demo.controller.generated.model.DeviceState::class.java)
        }
    }

    @Test
    fun testHandleSmartThingsCommand() {
        val id = "id"
        val deviceState = DeviceState("id", 7, true)
        val smartThingsDevice = SmartThingsDevice(id)
        every {
            conversionService.convert(
                smartThingsDevice,
                DeviceState::class.java
            )
        } returns deviceState
        every { smartHomeService.changeStates(listOf(deviceState), Provider.SMART_THINGS) } returns listOf(deviceState)
        val deviceState1 = com.mega.demo.controller.generated.model.DeviceState()
        every {
            conversionService.convert(
                deviceState,
                com.mega.demo.controller.generated.model.DeviceState::class.java
            )
        } returns deviceState1
        val body = executeRequest("commandRequest", listOf(smartThingsDevice))
        verifyHeaders(body, "commandResponse")
        assertSame(deviceState1, body!!.deviceState!![0])
        verifySequence {
            conversionService.convert(smartThingsDevice, DeviceState::class.java)
            smartHomeService.changeStates(listOf(deviceState), Provider.SMART_THINGS)
            conversionService.convert(deviceState, com.mega.demo.controller.generated.model.DeviceState::class.java)
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
    ): SmartThingsResponse? {
        val smartThingsRequest = SmartThingsRequest(
            Headers(interactionType = interactionType, requestId = requestId),
            Authentication(),
            smartThingsDevices
        )
        val response = delegate.handleSmartThings(smartThingsRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        return response.body
    }
}
