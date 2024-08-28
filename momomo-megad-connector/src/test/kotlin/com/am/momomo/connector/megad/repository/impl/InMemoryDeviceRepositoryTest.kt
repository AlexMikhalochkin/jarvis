package com.am.momomo.connector.megad.repository.impl

import com.am.momomo.model.DeviceState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Verification for [InMemoryDeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
internal class InMemoryDeviceRepositoryTest {

    private lateinit var deviceRepository: InMemoryDeviceRepository

    private val deviceId = "kitchen-light-0"

    @BeforeEach
    fun init() {
        deviceRepository = InMemoryDeviceRepository()
        deviceRepository.init()
    }

    @Disabled("Custom set of devices")
    @Test
    fun findAll() {
        val devices = deviceRepository.findAll()
        Assertions.assertNotNull(devices)
        Assertions.assertEquals(1, devices.size)
    }

    @Test
    fun testFindStates() {
        val states = deviceRepository.findStates(listOf(deviceId))
        Assertions.assertNotNull(states)
        Assertions.assertEquals(1, states.size)
        val deviceState = states[0]
        Assertions.assertEquals(deviceId, deviceState.deviceId)
        Assertions.assertFalse(deviceState.isOn!!)
    }

    @Test
    fun testUpdateStates() {
        deviceRepository.updateStates(listOf(DeviceState(deviceId, null, true)))
        val states = deviceRepository.findStates(listOf(deviceId))
        Assertions.assertNotNull(states)
        Assertions.assertEquals(1, states.size)
        val deviceState = states[0]
        Assertions.assertEquals(deviceId, deviceState.deviceId)
        Assertions.assertTrue(deviceState.isOn!!)
    }
}
