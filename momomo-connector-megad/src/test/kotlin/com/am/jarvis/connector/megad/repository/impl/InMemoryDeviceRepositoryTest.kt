package com.am.jarvis.connector.megad.repository.impl

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.core.model.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Verification for [InMemoryDeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
internal class InMemoryDeviceRepositoryTest {

    private val deviceId = "kitchen-light-0"

    private lateinit var deviceRepository: InMemoryDeviceRepository

    @BeforeEach
    fun setUp() {
        deviceRepository = InMemoryDeviceRepository(
            ConfiguredDevices(
                listOf(
                    Device(
                        deviceId,
                        Room("Kitchen"),
                        DeviceName("friendly name"),
                        "description",
                        listOf("Kitchen Lights", "House Bulbs"),
                        "MegaD",
                        mapOf("port" to 7)
                    )
                )
            )
        )
    }

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
        Assertions.assertFalse(deviceState.isOn)
    }

    @Test
    fun testUpdateStates() {
        deviceRepository.updateStates(listOf(DeviceState(deviceId, true, mapOf("port" to 7))))
        val states = deviceRepository.findStates(listOf(deviceId))

        assertNotNull(states)
        assertEquals(1, states.size)
        val deviceState = states[0]
        assertEquals(deviceId, deviceState.deviceId)
        assertTrue(deviceState.isOn)
    }

    @Test
    fun testUpdateStatesPortIsUnknown() {
        deviceRepository.updateStates(listOf(DeviceState(deviceId, true)))
        val states = deviceRepository.findStates(listOf(deviceId))

        assertNotNull(states)
        assertEquals(1, states.size)
        val deviceState = states[0]
        assertEquals(deviceId, deviceState.deviceId)
        assertTrue(deviceState.isOn)
    }

    @Test
    fun testGetDeviceByPort() {
        val device = deviceRepository.getDeviceByPort(7)

        assertNotNull(device)
        assertEquals(deviceId, device.id)
    }

    @Test
    fun testGetDeviceByPortNotFound() {
        assertThrows<NullPointerException> { deviceRepository.getDeviceByPort(6) }
    }

    @Test
    fun testFindPortByDeviceId() {
        assertEquals(7, deviceRepository.findPortByDeviceId(deviceId))
    }

    @Test
    fun testFindAllPorts() {
        val actual = deviceRepository.findAllPorts()
        assertEquals(1, actual.size)
        assertTrue(actual.contains(7))
    }
}
