package com.mega.demo.service.impl

import com.mega.demo.controller.model.smartthings.Command
import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.model.Device
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.impl.smartthings.SmartThingsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.core.convert.ConversionService

/**
 * Verification for [SmartThingsService].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsServiceTest {

    private lateinit var service: SmartThingsService
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var plcService: PlcService
    private lateinit var conversionService: ConversionService

    @BeforeEach
    fun init() {
        deviceRepository = mock()
        plcService = mock()
        conversionService = mock()
        service = SmartThingsService(deviceRepository, plcService, conversionService)
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
        val firstCommand = Command("st.switch", "on")
        val secondCommand = Command("st.switch", "off")
        val firstDevice = SmartThingsDevice("first", commands = listOf(firstCommand))
        val secondDevice = SmartThingsDevice("second", commands = listOf(secondCommand))
        whenever(deviceRepository.findPorts(listOf("first", "second"))).thenReturn(mapOf("first" to 1, "second" to 2))
        val deviceStates = service.executeCommands(listOf(firstDevice, secondDevice))
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
        val smartThingsDevice = mock<SmartThingsDevice>()
        whenever(conversionService.convert(any(), eq(SmartThingsDevice::class.java))).thenReturn(smartThingsDevice)
        assertSame(smartThingsDevice, service.getAllDevices()[0])
        verify(conversionService).convert(device, SmartThingsDevice::class.java)
    }

    private fun verifyDeviceState(deviceState: DeviceState, deviceId: String, expectedStateValue: String) {
        assertEquals(deviceId, deviceState.externalDeviceId)
        assertEquals(emptyMap<String, String>(), deviceState.deviceCookie)
        val states = deviceState.states
        assertEquals(1, states.size)
        val state = states[0]
        assertEquals("main", state.component)
        assertEquals(expectedStateValue, state.value)
        assertEquals("switch", state.attribute)
        assertEquals("st.switch", state.capability)
    }
}
