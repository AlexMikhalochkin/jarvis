package com.mega.demo.controller

import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.controller.generated.model.ChangeStatesRequest
import com.mega.demo.controller.generated.model.ChangeStatesRequestPayload
import com.mega.demo.controller.generated.model.ChangeStatesResponseDevice
import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.ShortYandexDevice
import com.mega.demo.controller.generated.model.StatesRequest
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceInfo
import com.mega.demo.controller.generated.model.YandexDeviceWithCapabilities
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.generateUuid
import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
import com.mega.demo.model.TechnicalInfo
import com.mega.demo.service.api.SmartHomeService
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus

/**
 * Verification for [YandexApiDelegateImpl].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class YandexApiDelegateImplTest {

    @MockK
    private lateinit var conversionService: ConversionService

    @MockK
    private lateinit var smartHomeService: SmartHomeService

    @InjectMockKs
    private lateinit var delegate: YandexApiDelegateImpl

    private val xRequestId = generateUuid()

    @AfterEach
    fun verify() = confirmVerified(conversionService, smartHomeService)

    @Test
    fun testChangeDevicesStates() {
        val fullCapability = FullCapability(YandexState("instance", true), "type")
        val changeStateDevice = ChangeStateDevice(generateUuid(), listOf(fullCapability))
        val changeStatesRequest = ChangeStatesRequest(ChangeStatesRequestPayload(listOf(changeStateDevice)))
        val deviceState = DeviceState("id", 1, true)
        every { conversionService.convert(changeStateDevice, DeviceState::class.java) } returns deviceState
        every { smartHomeService.changeStates(listOf(deviceState), Provider.YANDEX) } returns listOf(deviceState)
        val changeStatesResponseDevice = ChangeStatesResponseDevice()
        every {
            conversionService.convert(
                deviceState,
                ChangeStatesResponseDevice::class.java
            )
        } returns changeStatesResponseDevice
        val response = delegate.changeDevicesStates(generateUuid(), xRequestId, changeStatesRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(xRequestId, body!!.requestId)
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
        val response = delegate.getDevices(generateUuid(), xRequestId)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(xRequestId, body!!.requestId)
        assertEquals("user-id", body.payload.userId)
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
        val deviceState = DeviceState("id", 7, true)
        every { smartHomeService.getDeviceStates(listOf(id)) } returns listOf(deviceState)
        every {
            conversionService.convert(
                deviceState,
                YandexDeviceWithCapabilities::class.java
            )
        } returns YandexDeviceWithCapabilities()
        val response = delegate.getDevicesStates(generateUuid(), xRequestId, statesRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(xRequestId, body!!.requestId)
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
        val response = delegate.unlink(generateUuid(), xRequestId)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(xRequestId, response.body!!.requestId)
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

    private fun createDevice() = Device(
        "id",
        1,
        emptyMap(),
        emptyMap(),
        emptyMap(),
        TechnicalInfo("manufacturer", "model", "1.0", "1.0"),
        "description",
        emptyList(),
        emptyList(),
        emptyList()
    )
}
