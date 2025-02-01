package com.am.jarvis.connector.zigbee.processor

import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import io.mockk.Called
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification of the [ZigbeeSensorMessageProcessor].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class ZigbeeSensorMessageProcessorTest {

    @MockK(relaxUnitFun = true)
    private lateinit var notifier: Notifier

    private lateinit var processor: ZigbeeSensorMessageProcessor

    @BeforeEach
    fun setUp() {
        processor = ZigbeeSensorMessageProcessor(listOf(notifier))
    }

    @Test
    fun process() {
        val slot = slot<List<DeviceState>>()
        val message = """{"temperature": 15, "humidity": 60, "battery": 98}""".toByteArray()

        processor.process(message)

        verify { notifier.notify(capture(slot)) }
        assertEquals(1, slot.captured.size)
        val deviceState = slot.captured[0]
        assertEquals("child-sensor-1", deviceState.deviceId)
        assertEquals(15, deviceState.customData["temperature"])
        assertEquals(60, deviceState.customData["humidity"])
        assertEquals(98, deviceState.customData["battery"])
    }

    @Test
    fun getSupportedTopics() {
        val supportedTopics = processor.getSupportedTopics()

        assertEquals(1, supportedTopics.size)
        assertEquals("zigbee2mqtt/Temperature and humidity sensor", supportedTopics[0])
        verify { notifier wasNot Called }
    }
}
