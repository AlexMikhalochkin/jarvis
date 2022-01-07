package com.am.jarvis.repository.impl

import com.am.jarvis.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Verification for [InMemoryDeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
internal class InMemoryDeviceRepositoryTest {

    private lateinit var deviceRepository: InMemoryDeviceRepository

    @BeforeEach
    fun init() {
        deviceRepository = InMemoryDeviceRepository()
        deviceRepository.init()
    }

    @Test
    fun findAll() {
        val devices = deviceRepository.findAll()
        assertNotNull(devices)
        assertEquals(1, devices.size)
    }

    private val deviceId = "kitchen-light-0"

    @Test
    fun findPorts() {
        val ports = deviceRepository.findPorts(listOf(deviceId))
        assertNotNull(ports)
        assertEquals(1, ports.size)
        assertEquals(7, ports[deviceId])
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
        deviceRepository.updateStates(listOf(DeviceState(deviceId, null, true)))
        val states = deviceRepository.findStates(listOf(deviceId))
        assertNotNull(states)
        assertEquals(1, states.size)
        val deviceState = states[0]
        assertEquals(deviceId, deviceState.deviceId)
        assertTrue(deviceState.isOn!!)
    }
}
