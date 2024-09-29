package com.am.jarvis.connector.yandex.controller

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
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.core.model.Room
import com.am.jarvis.core.api.SmartHomeService
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import java.util.UUID

/**
 * Verification for [YandexApiDelegateImpl].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class YandexApiDelegateImplTest {

    private val userId = "user-id"
    private val requestId = UUID.randomUUID().toString()
    private val deviceId = UUID.randomUUID().toString()

    @MockK
    private lateinit var conversionService: ConversionService

    @MockK
    private lateinit var smartHomeService: SmartHomeService

    @InjectMockKs
    private lateinit var delegate: YandexApiDelegateImpl

    @BeforeEach
    fun init() {
        delegate = YandexApiDelegateImpl(smartHomeService, conversionService, userId)
    }

    @AfterEach
    fun verify() = confirmVerified(conversionService, smartHomeService)

    @Test
    fun testChangeDevicesStates() {
        val fullCapability = FullCapability(YandexState("instance", true), "type")
        val changeStateDevice = ChangeStateDevice(UUID.randomUUID().toString(), listOf(fullCapability))
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
        val response = delegate.changeDevicesStates(UUID.randomUUID().toString(), requestId, changeStatesRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(requestId, body!!.requestId)
        assertSame(changeStatesResponseDevice, body.payload!!.devices!![0])
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
        val response = delegate.getDevices(UUID.randomUUID().toString(), requestId)
        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertEquals(requestId, body!!.requestId)
        assertEquals(userId, body.payload.userId)
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
        val response = delegate.getDevicesStates(UUID.randomUUID().toString(), requestId, statesRequest)
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
        val response = delegate.unlink(UUID.randomUUID().toString(), requestId)
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

    private fun createDevice() = Device(
        deviceId,
        Room("room"),
        DeviceName("device"),
        "description",
        emptyList()
    )

    private fun createDeviceState() = DeviceState(deviceId, true, mapOf("port" to 1))
}
