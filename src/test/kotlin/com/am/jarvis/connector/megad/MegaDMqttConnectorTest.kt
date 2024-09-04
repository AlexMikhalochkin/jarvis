package com.am.jarvis.connector.megad

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
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
    private lateinit var deviceRepository: DeviceRepository

    @MockK(relaxUnitFun = true)
    private lateinit var mqttClient: IMqttClient

    private lateinit var connector: MegaDMqttConnector

    @BeforeEach
    fun setUp() {
        connector = MegaDMqttConnector(deviceRepository, mqttClient, "topic/cmd")
    }

    @Test
    fun getAllDevices() {
        val device: Device = mockk()
        val devices = listOf(device, device)
        every { deviceRepository.findAll() } returns devices

        val result = connector.getAllDevices()

        assertSame(devices, result)
    }

    @Test
    fun getDeviceStates() {
        val deviceIds = listOf("1", "2")
        val state: DeviceState = mockk()
        val states = listOf(state, state)
        every { deviceRepository.findStates(deviceIds) } returns states

        val result = connector.getDeviceStates(deviceIds)

        assertSame(states, result)
    }

    @Test
    fun changeStates() {
        val states = listOf(
            DeviceState("1", true, mapOf("port" to 1)),
            DeviceState("2", false, mapOf("port" to 2))
        )

        connector.changeStates(states)

        verify { mqttClient.publish("topic/cmd", "1:1".toByteArray(), 0, true) }
        verify { mqttClient.publish("topic/cmd", "2:0".toByteArray(), 0, true) }
    }

    @Test
    fun changeStatesPortIsUnknown() {
        val states = listOf(
            DeviceState("1", true),
            DeviceState("2", false)
        )
        every { deviceRepository.findPortByDeviceId("1") } returns 1
        every { deviceRepository.findPortByDeviceId("2") } returns 2

        connector.changeStates(states)

        verify { mqttClient.publish("topic/cmd", "1:1".toByteArray(), 0, true) }
        verify { mqttClient.publish("topic/cmd", "2:0".toByteArray(), 0, true) }
    }

    @Test
    fun getSource() {
        assertSame("MegaD", connector.getSource())
    }

    @Test
    fun areNotificationsEnabled() {
        assertSame(true, connector.areNotificationsEnabled())
    }
}
