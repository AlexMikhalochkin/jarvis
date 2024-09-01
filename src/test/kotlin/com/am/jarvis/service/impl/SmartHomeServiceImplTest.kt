package com.am.jarvis.service.impl

import com.am.jarvis.service.api.NotificationService
import com.am.momomo.connector.api.DeviceProvider
import com.am.momomo.connector.api.DeviceStateChanger
import com.am.momomo.connector.api.DeviceStateProvider
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [SmartHomeServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class SmartHomeServiceImplTest {

    private val deviceId = "first"

    @MockK(relaxUnitFun = true)
    lateinit var notificationService: NotificationService
    @MockK
    lateinit var deviceProvider: DeviceProvider
    @MockK
    lateinit var deviceStateProvider: DeviceStateProvider
    @MockK
    lateinit var deviceStateChanger: DeviceStateChanger

    private lateinit var service: SmartHomeServiceImpl

    @BeforeEach
    fun setUp() {
        service = SmartHomeServiceImpl(
            listOf(deviceProvider),
            listOf(deviceStateProvider),
            listOf(deviceStateChanger),
            notificationService
        )
    }

    @Test
    fun getAllDevices() {
        val devices: List<Device> = listOf(mockk())
        every { deviceProvider.getAllDevices() } returns devices
        assertEquals(devices, service.getAllDevices())
        verify { deviceProvider.getAllDevices() }
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf(deviceId, "second")
        val states = listOf(DeviceState(deviceId, true), DeviceState("second", false))
        every { deviceStateProvider.getDeviceStates(deviceIds) } returns states
        assertEquals(states, service.getDeviceStates(deviceIds))
        verify { deviceStateProvider.getDeviceStates(deviceIds) }
    }

    @Test
    fun testChangeStates() {
        val states = listOf(DeviceState(deviceId, true), DeviceState("second", false))
        val provider = Provider.YANDEX
        every { deviceStateChanger.changeStates(states) } returns states
        service.changeStates(states, provider)
        verifySequence {
            deviceStateChanger.changeStates(states)
            notificationService.notifyProviders(states, provider)
        }
    }
}
