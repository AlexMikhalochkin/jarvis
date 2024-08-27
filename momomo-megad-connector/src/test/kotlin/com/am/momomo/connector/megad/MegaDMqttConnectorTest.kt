package com.am.momomo.connector.megad

import com.am.momomo.connector.megad.client.mqtt.MessageSender
import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertSame

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [MegaDMqttConnector].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class MegaDMqttConnectorTest {

    @MockK
    lateinit var deviceRepository: DeviceRepository

    @MockK(relaxUnitFun = true)
    lateinit var messageSender: MessageSender

    @InjectMockKs
    lateinit var connector: MegaDMqttConnector

    @Test
    fun getAllDevices() {
        val device:Device = mockk()
        val devices = listOf(device, device)
        every { deviceRepository.findAll() } returns devices

        val result = connector.getAllDevices()

        assertSame(devices, result)
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf("1", "2")
        val state:DeviceState = mockk()
        val states = listOf(state, state)
        every { deviceRepository.findStates(deviceIds) } returns states

        val result = connector.getDeviceStates(deviceIds)

        assertSame(states, result)
    }

    @Test
    fun changeStates() {
        val states = listOf(DeviceState("1", 1, true), DeviceState("2", 2, false))

        connector.changeStates(states)

        verify { messageSender.send("1:1") }
        verify { messageSender.send("2:0") }
    }
}
