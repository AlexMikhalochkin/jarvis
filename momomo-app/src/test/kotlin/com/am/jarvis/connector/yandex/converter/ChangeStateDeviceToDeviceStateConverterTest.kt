package com.am.jarvis.connector.yandex.converter

import com.am.jarvis.connector.BaseConverterTest
import com.am.jarvis.controller.generated.model.ChangeStateDevice
import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.core.model.DeviceState

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

    override fun createExpected() = DeviceState("id", true, mapOf("port" to 1))
}
