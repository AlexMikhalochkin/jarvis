package com.mega.demo.service.impl

import com.mega.demo.integration.api.MessageSender
import com.mega.demo.integration.api.YandexCallbackClient
import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.repository.api.DeviceRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Verification for [SmartHomeServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartHomeServiceImplTest {

    private lateinit var service: SmartHomeServiceImpl
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var messageSender: MessageSender
    private lateinit var yandexCallbackClient: YandexCallbackClient

    @BeforeEach
    fun init() {
        deviceRepository = mock()
        messageSender = mock()
        yandexCallbackClient = mock()
        service = SmartHomeServiceImpl(deviceRepository, messageSender, yandexCallbackClient)
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf("first", "second")
        whenever(deviceRepository.findPorts(deviceIds)).thenReturn(mapOf("first" to 1, "second" to 2))
        whenever(deviceRepository.findStatuses(any())).thenReturn(mapOf(1 to true, 2 to false))
        val deviceStates = service.getDeviceStates(deviceIds)
        assertEquals(2, deviceStates.size)
        verifyDeviceState(deviceStates[0], "first", true)
        verifyDeviceState(deviceStates[1], "second", false)
        verify(deviceRepository).findPorts(deviceIds)
    }

    @Test
    fun getAllDevices() {
        val device = mock<Device>()
        val devices: List<Device> = listOf(device)
        whenever(deviceRepository.findAll()).thenReturn(devices)
        assertSame(device, service.getAllDevices()[0])
    }

    private fun verifyDeviceState(deviceState: DeviceState, deviceId: String, expectedStateValue: Boolean) {
        assertEquals(deviceId, deviceState.deviceId)
        assertEquals(expectedStateValue, deviceState.isOn)
    }
}
