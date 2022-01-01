package com.am.jarvis.controller

import com.am.jarvis.controller.generated.model.ChangeStateDevice
import com.am.jarvis.controller.generated.model.ChangeStatesRequest
import com.am.jarvis.controller.generated.model.ChangeStatesRequestPayload
import com.am.jarvis.controller.generated.model.ChangeStatesResponseDevice
import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.ShortYandexDevice
import com.am.jarvis.controller.generated.model.StatesRequest
import com.am.jarvis.controller.generated.model.YandexDevice
import com.am.jarvis.controller.generated.model.YandexDeviceInfo
import com.am.jarvis.controller.generated.model.YandexDeviceWithCapabilities
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.generateUuid
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.util.ReflectionTestUtils

private const val USER_ID = "user-id"

/**
 * Verification for [YandexApiDelegateImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class YandexApiDelegateImplTest : BaseDelegateTest<YandexApiDelegateImpl>() {

    @BeforeEach
    fun init() {
        ReflectionTestUtils.setField(delegate, "userId", USER_ID)
    }

    @Test
    fun testChangeDevicesStates() {
        val fullCapability = FullCapability(YandexState("instance", true), "type")
        val changeStateDevice = ChangeStateDevice(generateUuid(), listOf(fullCapability))
        val changeStatesRequest = ChangeStatesRequest(ChangeStatesRequestPayload(listOf(changeStateDevice)))
        val deviceState = createDeviceState()
        every { conversionService.convert(changeStateDevice, DeviceState::class.java) } returns deviceState
        every { smartHomeService.changeStates(listOf(deviceState), Provider.YANDEX) } returns listOf(deviceState)
        val changeStatesResponseDevice = ChangeStatesResponseDevice()
        every {
            conversionService.convert(
                deviceState,
                ChangeStatesResponseDevice::class.java
            )
        } returns changeStatesResponseDevice
        val response = delegate.changeDevicesStates(generateUuid(), requestId, changeStatesRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(requestId, body!!.requestId)
        assertSame(changeStatesResponseDevice, body.payload!!.devices!![0])
        verifySequence {
            conversionService.convert(changeStateDevice, DeviceState::class.java)
            smartHomeService.changeStates(listOf(deviceState), Provider.YANDEX)
            conversionService.convert(deviceState, ChangeStatesResponseDevice::class.java)
        }
    }

    @Test
    fun testGetDevices() {
        val device = createDevice()
        every { smartHomeService.getAllDevices() } returns listOf(device)
        val yandexDevice = createYandexDevice()
        every {
            conversionService.convert(
                device,
                YandexDevice::class.java
            )
        } returns yandexDevice
        val response = delegate.getDevices(generateUuid(), requestId)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(requestId, body!!.requestId)
        assertEquals(USER_ID, body.payload.userId)
        assertSame(yandexDevice, body.payload.devices[0])
        verifySequence {
            smartHomeService.getAllDevices()
            conversionService.convert(device, YandexDevice::class.java)
        }
    }

    @Test
    fun testGetDevicesStates() {
        val id = "id"
        val statesRequest = StatesRequest(listOf(ShortYandexDevice(id), ShortYandexDevice(null)))
        val deviceState = createDeviceState()
        every { smartHomeService.getDeviceStates(listOf(id)) } returns listOf(deviceState)
        every {
            conversionService.convert(
                deviceState,
                YandexDeviceWithCapabilities::class.java
            )
        } returns YandexDeviceWithCapabilities()
        val response = delegate.getDevicesStates(generateUuid(), requestId, statesRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(requestId, body!!.requestId)
        assertEquals(YandexDeviceWithCapabilities(), body.payload!!.devices!![0])
        verifySequence {
            smartHomeService.getDeviceStates(listOf(id))
            conversionService.convert(deviceState, YandexDeviceWithCapabilities::class.java)
        }
    }

    @Test
    fun testHealth() {
        val response = delegate.health()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Unit, response.body)
    }

    @Test
    fun testUnlink() {
        val response = delegate.unlink(generateUuid(), requestId)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(requestId, response.body!!.requestId)
    }

    private fun createYandexDevice() = YandexDevice(
        "name",
        "type",
        emptyList(),
        YandexDeviceInfo(
            "manufacturer",
            "model"
        ),
        "id",
    )
}
