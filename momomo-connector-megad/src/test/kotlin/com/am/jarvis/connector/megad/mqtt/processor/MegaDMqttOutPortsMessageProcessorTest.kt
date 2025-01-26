package com.am.jarvis.connector.megad.mqtt.processor

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Tests for [MegaDMqttOutPortsMessageProcessor].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class MegaDMqttOutPortsMessageProcessorTest {

    @MockK(relaxUnitFun = true)
    private lateinit var notifier: Notifier

    @MockK(relaxUnitFun = true)
    private lateinit var deviceRepository: DeviceRepository

    private lateinit var processor: MegaDMqttOutPortsMessageProcessor

    private val megadId = "mega-id"

    @BeforeEach
    fun setUp() {
        processor = MegaDMqttOutPortsMessageProcessor(megadId, deviceRepository, listOf(notifier))
    }

    @Test
    fun process() {
        val port = 1
        val deviceId = "device-id"
        val message = """{"port":$port,"value":"ON"}""".toByteArray()
        val deviceStates = listOf(DeviceState(deviceId, true))
        val device = mockk<Device>()
        every { deviceRepository.getDeviceByPort(port) } returns device
        every { device.id } returns deviceId

        processor.process(message)

        verify { deviceRepository.updateStates(deviceStates) }
        verify { notifier.notify(deviceStates) }
    }

    @Test
    fun getSupportedTopics() {
        every { deviceRepository.findAllPorts() } returns listOf(1, 2, 3)

        val result = processor.getSupportedTopics()

        assertEquals(listOf("$megadId/1", "$megadId/2", "$megadId/3"), result)
        verify { deviceRepository.findAllPorts() }
        verify { notifier wasNot Called }
    }
}
