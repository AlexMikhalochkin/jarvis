package com.am.jarvis.repository.impl

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * Verification for [InMemoryDeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
internal class InMemoryDeviceRepositoryTest {

    @Test
    fun findAll() {
        val deviceRepository = InMemoryDeviceRepository()
        deviceRepository.init()
        val devices = deviceRepository.findAll()
        assertNotNull(devices)
    }

    @Test
    fun findPorts() {
        val deviceRepository = InMemoryDeviceRepository()
        deviceRepository.init()
        val ports = deviceRepository.findPorts(listOf("kitchen-light-0"))
        assertNotNull(ports)
    }
}
