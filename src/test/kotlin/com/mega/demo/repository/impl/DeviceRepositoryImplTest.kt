package com.mega.demo.repository.impl

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceRepositoryImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceRepositoryImplTest {

    @Test
    fun findAll() {
        val deviceRepository = DeviceRepositoryImpl()
        val devices = deviceRepository.findAll()
        assertNotNull(devices)
    }

    @Test
    fun findPorts() {
        val deviceRepository = DeviceRepositoryImpl()
        val ports = deviceRepository.findPorts(listOf("test"))
        assertNotNull(ports)
    }
}
