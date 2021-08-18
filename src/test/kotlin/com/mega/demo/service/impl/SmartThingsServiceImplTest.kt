package com.mega.demo.service.impl

import com.mega.demo.controller.generated.model.Command
import com.mega.demo.controller.generated.model.DeviceState
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.Device
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.core.convert.ConversionService

/**
 * Verification for [SmartThingsServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsServiceImplTest {

    private lateinit var service: SmartThingsServiceImpl
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var plcService: PlcService
    private lateinit var conversionService: ConversionService

    @BeforeEach
    fun init() {
        deviceRepository = mock()
        plcService = mock()
        conversionService = mock()
        service = SmartThingsServiceImpl(deviceRepository, plcService, conversionService)
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf("first", "second")
        whenever(plcService.getPortStatuses()).thenReturn(mapOf(1 to true, 2 to false))
        whenever(deviceRepository.findPorts(deviceIds)).thenReturn(mapOf("first" to 1, "second" to 2))
        val deviceStates = service.getDeviceStates(deviceIds)
        assertEquals(2, deviceStates.size)
        verifyDeviceState(deviceStates[0], "first", "on")
        verifyDeviceState(deviceStates[1], "second", "off")
        verify(plcService).getPortStatuses()
        verify(deviceRepository).findPorts(deviceIds)
    }

    @Test
    fun changeStatus() {
        val firstCommand = Command(capability = "st.switch", command = "on")
        val secondCommand = Command(capability = "st.switch", command = "off")
        val firstDevice = SmartThingsDevice("first", commands = listOf(firstCommand))
        val secondDevice = SmartThingsDevice("second", commands = listOf(secondCommand))
        whenever(deviceRepository.findPorts(listOf("first", "second"))).thenReturn(mapOf("first" to 1, "second" to 2))
        val deviceStates = service.changeState(listOf(firstDevice, secondDevice))
        assertEquals(2, deviceStates.size)
        verifyDeviceState(deviceStates[0], "first", "on")
        verifyDeviceState(deviceStates[1], "second", "off")
        verify(plcService).turnOn(1)
        verify(plcService).turnOff(2)
    }

    @Test
    fun getAllDevices() {
        val device = mock<Device>()
        val devices: List<Device> = listOf(device)
        whenever(deviceRepository.findAll()).thenReturn(devices)
        assertSame(device, service.getAllDevices()[0])
    }

    private fun verifyDeviceState(deviceState: DeviceState, deviceId: String, expectedStateValue: String) {
        assertEquals(deviceId, deviceState.externalDeviceId)
        assertEquals(emptyMap<String, Any>(), deviceState.deviceCookie)
        val states = deviceState.states
        assertEquals(1, states!!.size)
        val state = states[0]
        assertEquals("main", state.component)
        assertEquals(expectedStateValue, state.value)
        assertEquals("switch", state.attribute)
        assertEquals("st.switch", state.capability)
    }
}
