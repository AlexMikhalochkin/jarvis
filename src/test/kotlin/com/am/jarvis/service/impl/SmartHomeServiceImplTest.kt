package com.am.jarvis.service.impl

import com.am.jarvis.integration.api.MessageSender
import com.am.jarvis.model.Device
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import com.am.jarvis.repository.api.DeviceRepository
import com.am.jarvis.service.api.NotificationService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [SmartHomeServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class SmartHomeServiceImplTest {

    private val port = 7
    private val deviceId = "first"

    @MockK(relaxUnitFun = true)
    lateinit var deviceRepository: DeviceRepository

    @MockK(relaxUnitFun = true)
    lateinit var messageSender: MessageSender

    @MockK(relaxUnitFun = true)
    lateinit var notificationService: NotificationService

    @InjectMockKs
    lateinit var service: SmartHomeServiceImpl

    @Test
    fun getAllDevices() {
        val devices: List<Device> = listOf(mockk())
        every { deviceRepository.findAll() } returns devices
        assertSame(devices, service.getAllDevices())
        verify { deviceRepository.findAll() }
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf(deviceId, "second")
        val states = listOf(DeviceState(deviceId, null, true), DeviceState("second", null, false))
        every { deviceRepository.findStates(deviceIds) } returns states
        assertSame(states, service.getDeviceStates(deviceIds))
        verify { deviceRepository.findStates(deviceIds) }
    }

    @Test
    fun testChangeStates() {
        val states = listOf(DeviceState(deviceId, port, true), DeviceState("second", 11, false))
        val provider = Provider.YANDEX
        service.changeStates(states, provider)
        verifySequence {
            deviceRepository.updateStates(states)
            messageSender.send("7:1")
            messageSender.send("11:0")
            notificationService.notifyProviders(states, provider)
        }
    }
}
