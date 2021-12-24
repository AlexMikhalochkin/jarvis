package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexDeviceWithCapabilities
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.model.DeviceState

/**
 * Verification for [DeviceToYandexDeviceWithCapabilitiesConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToYandexDeviceWithCapabilitiesConverterTest :
    BaseConverterTest<DeviceState, YandexDeviceWithCapabilities>() {

    override fun getConverter() = DeviceToYandexDeviceWithCapabilitiesConverter()

    override fun createSource() = DeviceState("id", 1, true)

    override fun createExpected(): YandexDeviceWithCapabilities {
        return YandexDeviceWithCapabilities(
            "id",
            listOf(FullCapability(YandexState("on", true), "devices.capabilities.on_off"))
        )
    }
}
