package com.am.jarvis.connector.yandex.notifier.converter

import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.core.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converter for capabilities
 *
 * @author Alex Mikhalochkin
 */
@Component
class CapabilitiesConverter : Converter<DeviceState, List<FullCapability>> {

    override fun convert(source: DeviceState): List<FullCapability> {
        return listOfNotNull(convertProperty(source))
    }

    private fun convertProperty(source: DeviceState): FullCapability? {
        if (source.customData.containsKey("battery")) return null
        return FullCapability(
            YandexState("on", source.isOn),
            "devices.capabilities.on_off"
        )
    }
}
