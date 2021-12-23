package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.model.DeviceState

/**
 * Verification for [ChangeStateDeviceToDeviceStateConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class ChangeStateDeviceToDeviceStateConverterTest : BaseConverterTest<ChangeStateDevice, DeviceState>() {

    override fun getConverter() = ChangeStateDeviceToDeviceStateConverter()

    override fun createSource(): ChangeStateDevice {
        val capability = FullCapability(
            YandexState("instance", true),
            "type"
        )
        return ChangeStateDevice(
            "id",
            listOf(capability),
            mapOf("port" to 1)
        )
    }

    override fun createExpected() = DeviceState("id", 1, true)
}
