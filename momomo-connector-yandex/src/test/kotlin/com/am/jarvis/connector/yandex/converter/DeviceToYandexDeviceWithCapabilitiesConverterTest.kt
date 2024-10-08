package com.am.jarvis.connector.yandex.converter

import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.YandexDeviceWithCapabilities
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.core.model.DeviceState

/**
 * Verification for [DeviceToYandexDeviceWithCapabilitiesConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToYandexDeviceWithCapabilitiesConverterTest :
    BaseConverterTest<DeviceState, YandexDeviceWithCapabilities>() {

    override fun getConverter() = DeviceToYandexDeviceWithCapabilitiesConverter()

    override fun createSource() = DeviceState("id", true, mapOf("port" to 1))

    override fun createExpected(): YandexDeviceWithCapabilities {
        return YandexDeviceWithCapabilities(
            "id",
            listOf(FullCapability(YandexState("on", true), "devices.capabilities.on_off"))
        )
    }
}
