package com.am.jarvis.connector.megad.repository.impl

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.core.model.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Verification for [InMemoryDeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
internal class InMemoryDeviceRepositoryTest {

    private val deviceId = "kitchen-light-0"

    private val deviceRepository: InMemoryDeviceRepository = InMemoryDeviceRepository(
        ConfiguredDevices(
            listOf(
                Device(
                    deviceId,
                    Room("Kitchen"),
                    DeviceName("friendly name"),
                    "description",
                    listOf("Kitchen Lights", "House Bulbs"),
                    mapOf("port" to 7)
                )
            )
        )
    )

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
        Assertions.assertFalse(deviceState.isOn)
    }

    @Test
    fun testUpdateStates() {
        deviceRepository.updateStates(listOf(DeviceState(deviceId, true)))
        val states = deviceRepository.findStates(listOf(deviceId))

        Assertions.assertNotNull(states)
        Assertions.assertEquals(1, states.size)
        val deviceState = states[0]
        Assertions.assertEquals(deviceId, deviceState.deviceId)
        Assertions.assertTrue(deviceState.isOn)
    }
}
