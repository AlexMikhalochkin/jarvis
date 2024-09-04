package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.core.model.DeviceState
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [SmartThingsNotifier].
 *
 * @author Alex Mikhalochkin
 */
class SmartThingsNotifierTest {

    private val notifier = SmartThingsNotifier()

    @Test
    fun logsDeviceStatesWhenNotifying() {
        val states = listOf(DeviceState("device1", true), DeviceState("device2", false))
        assertDoesNotThrow { notifier.notify(states) }
    }

    @Test
    fun returnsCorrectSource() {
        assertEquals("SMART_THINGS", notifier.getSource())
    }
}
