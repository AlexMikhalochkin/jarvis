package com.mega.demo.controller.converter

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.State
import com.mega.demo.model.DeviceStatus
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceStateConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceStateConverterTest {

    private val externalDeviceId = UUID.randomUUID().toString()

    @Test
    fun testConvert() {
        assertEquals(createExpected("on"), DeviceStateConverter().convert(createSource(true)))
    }

    @Test
    fun testConvertOff() {
        assertEquals(createExpected("off"), DeviceStateConverter().convert(createSource(false)))
    }

    private fun createExpected(state: String): DeviceState {
        return DeviceState(
            externalDeviceId,
            listOf(State("main", "st.switch", "switch", state))
        )
    }

    private fun createSource(status: Boolean) = DeviceStatus(externalDeviceId, status)
}
