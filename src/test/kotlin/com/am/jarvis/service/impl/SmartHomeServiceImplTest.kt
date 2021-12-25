package com.am.jarvis.service.impl

import com.am.jarvis.integration.api.MessageSender
import com.am.jarvis.model.Device
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import com.am.jarvis.repository.api.DeviceRepository
import com.am.jarvis.service.api.NotificationService
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Verification for [SmartHomeServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartHomeServiceImplTest {

    private val port = 7
    private val deviceId = "first"

    private lateinit var service: SmartHomeServiceImpl
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var messageSender: MessageSender
    private lateinit var notificationService: NotificationService

    @BeforeEach
    fun init() {
        deviceRepository = mock()
        messageSender = mock()
        notificationService = mock()
        service = SmartHomeServiceImpl(deviceRepository, messageSender, notificationService)
    }

    @Test
    fun getAllDevices() {
        val devices: List<Device> = listOf(mock())
        whenever(deviceRepository.findAll()).thenReturn(devices)
        assertSame(devices, service.getAllDevices())
        verify(deviceRepository).findAll()
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf(deviceId, "second")
        val states = listOf(DeviceState(deviceId, null, true), DeviceState("second", null, false))
        whenever(deviceRepository.findStates(deviceIds)).thenReturn(states)
        assertSame(states, service.getDeviceStates(deviceIds))
        verify(deviceRepository).findStates(deviceIds)
    }

    @Test
    fun testChangeStates() {
        val states = listOf(DeviceState(deviceId, port, true), DeviceState("second", 11, false))
        val provider = Provider.YANDEX
        service.changeStates(states, provider)
        verify(deviceRepository).updateStates(states)
        verify(messageSender).send("7:1")
        verify(messageSender).send("11:0")
        verify(notificationService).notifyProviders(states, provider)
    }
}
