package com.am.jarvis.service.impl

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.Room
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryDeviceSourceRepositoryTest {

    private lateinit var repository: InMemoryDeviceSourceRepository

    @BeforeEach
    fun setUp() {
        repository = InMemoryDeviceSourceRepository()
    }

    @Test
    fun saveSingleDevice() {
        val device = getDevice("1", "sourceA")
        repository.save(listOf(device))
        val result = repository.getDevicesPerSource(listOf("1"))
        assertEquals(mapOf("sourceA" to listOf("1")), result)
    }

    @Test
    fun saveMultipleDevices() {
        val devices = listOf(
            getDevice("1", "sourceA"),
            getDevice("2", "sourceB"),
            getDevice("3", "sourceA")
        )
        repository.save(devices)
        val result = repository.getDevicesPerSource(listOf("1", "2", "3"))
        assertEquals(mapOf("sourceA" to listOf("1", "3"), "sourceB" to listOf("2")), result)
    }

    @Test
    fun getDevicesPerSourceWithEmptyInput() {
        val result = repository.getDevicesPerSource(emptyList())
        assertEquals(emptyMap<String, List<String>>(), result)
    }

    @Test
    fun getDevicesPerSourceWithNonExistentIds() {
        val devices = listOf(
            getDevice("1", "sourceA"),
            getDevice("2", "sourceB")
        )
        repository.save(devices)
        val result = repository.getDevicesPerSource(listOf("3", "4"))
        assertEquals(emptyMap<String, List<String>>(), result)
    }

    @Test
    fun getDevicesPerSourceWithMixedIds() {
        val devices = listOf(
            getDevice("1", "sourceA"),
            getDevice("2", "sourceB")
        )
        repository.save(devices)
        val result = repository.getDevicesPerSource(listOf("1", "3"))
        assertEquals(mapOf("sourceA" to listOf("1")), result)
    }

    private fun getDevice(deviceId: String, sourceChannel: String): Device {
        return Device(
            deviceId,
            Room("Kitchen"),
            DeviceName("name"),
            "name",
            listOf("Kitchen Lights"),
            sourceChannel,
            mapOf("port" to 7)
        )
    }
}
