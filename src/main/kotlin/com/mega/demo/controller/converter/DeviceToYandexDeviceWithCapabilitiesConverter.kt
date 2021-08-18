package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexDeviceWithCapabilities
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceState] to [YandexDeviceWithCapabilities].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToYandexDeviceWithCapabilitiesConverter : Converter<DeviceState, YandexDeviceWithCapabilities> {

    override fun convert(source: DeviceState): YandexDeviceWithCapabilities {
        return YandexDeviceWithCapabilities(
            source.deviceId,
            convertCapabilities(source.isOn!!)
        )
    }

    private fun convertCapabilities(isOn: Boolean): List<FullCapability> {
        return listOf(FullCapability(YandexState("on", isOn), "devices.capabilities.on_off"))
    }
}
