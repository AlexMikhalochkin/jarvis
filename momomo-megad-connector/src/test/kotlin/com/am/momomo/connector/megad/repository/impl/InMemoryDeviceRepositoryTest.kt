package com.am.momomo.connector.megad.repository.impl

import com.am.momomo.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
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
    }

    @Disabled("Custom set of devices")
    @Test
    fun findAll() {
        val devices = deviceRepository.findAll()

        assertNotNull(devices)
        assertEquals(1, devices.size)
    }

    @Test
    fun testFindStates() {
        val states = deviceRepository.findStates(listOf(deviceId))

        assertNotNull(states)
        assertEquals(1, states.size)
        val deviceState = states[0]
        assertEquals(deviceId, deviceState.deviceId)
        assertFalse(deviceState.isOn!!)
    }

    @Test
    fun testUpdateStates() {
        deviceRepository.updateStates(listOf(DeviceState(deviceId, true)))
        val states = deviceRepository.findStates(listOf(deviceId))

        assertNotNull(states)
        assertEquals(1, states.size)
        val deviceState = states[0]
        assertEquals(deviceId, deviceState.deviceId)
        assertTrue(deviceState.isOn!!)
    }
}
