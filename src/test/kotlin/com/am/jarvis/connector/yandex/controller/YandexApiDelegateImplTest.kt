package com.am.jarvis.connector.yandex.controller

import com.am.jarvis.connector.BaseDelegateTest
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
import com.am.momomo.model.DeviceState
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions
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
        every { smartHomeService.changeStates(listOf(deviceState), "YANDEX") } returns listOf(deviceState)
        val changeStatesResponseDevice = ChangeStatesResponseDevice()
        every {
            conversionService.convert(
                deviceState,
                ChangeStatesResponseDevice::class.java
            )
        } returns changeStatesResponseDevice
        val response = delegate.changeDevicesStates(generateUuid(), requestId, changeStatesRequest)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        Assertions.assertEquals(requestId, body!!.requestId)
        Assertions.assertSame(changeStatesResponseDevice, body.payload!!.devices!![0])
        verifySequence {
            conversionService.convert(changeStateDevice, DeviceState::class.java)
            smartHomeService.changeStates(listOf(deviceState), "YANDEX")
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
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        Assertions.assertEquals(requestId, body!!.requestId)
        Assertions.assertEquals(USER_ID, body.payload.userId)
        Assertions.assertSame(yandexDevice, body.payload.devices[0])
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
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        Assertions.assertEquals(requestId, body!!.requestId)
        Assertions.assertEquals(YandexDeviceWithCapabilities(), body.payload!!.devices!![0])
        verifySequence {
            smartHomeService.getDeviceStates(listOf(id))
            conversionService.convert(deviceState, YandexDeviceWithCapabilities::class.java)
        }
    }

    @Test
    fun testHealth() {
        val response = delegate.health()
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(Unit, response.body)
    }

    @Test
    fun testUnlink() {
        val response = delegate.unlink(generateUuid(), requestId)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(requestId, response.body!!.requestId)
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
