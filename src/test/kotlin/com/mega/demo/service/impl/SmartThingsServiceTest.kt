package com.mega.demo.service.impl

import com.mega.demo.model.Device
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.impl.smartthings.SmartThingsService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Verification for [SmartThingsService].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsServiceTest {

    private lateinit var service: SmartThingsService
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var plcService: PlcService

    @BeforeEach
    fun init() {
        deviceRepository = mock()
        plcService = mock()
        service = SmartThingsService(deviceRepository, plcService)
    }

    @Test
    fun getStatuses() {
        assertNotNull(service.getStatuses(emptyList()))
    }

    @Test
    fun changeStatus() {
        service.changeStatus(emptyList(), true)
    }

    @Test
    fun getAllDevices() {
        val devices: List<Device> = emptyList()
        whenever(deviceRepository.findAll()).thenReturn(devices)
        assertSame(devices, service.getAllDevices())
    }
}
