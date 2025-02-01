package com.am.jarvis.connector.yandex.notifier.converter

import com.am.jarvis.controller.generated.model.ChangeStatesDevice
import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.Property
import com.am.jarvis.core.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converter for Yandex states
 *
 * @author Alex Mikhalochkin
 */
@Component
class StateConverter(
    private val capabilitiesConverter: Converter<DeviceState, List<*>>,
    private val propertiesConverter: Converter<DeviceState, List<*>>
) : Converter<DeviceState, ChangeStatesDevice> {

    override fun convert(source: DeviceState): ChangeStatesDevice {
        return ChangeStatesDevice(
            source.deviceId,
            (capabilitiesConverter.convert(source) ?: emptyList<Any>()) as List<FullCapability>,
            propertiesConverter.convert(source) as List<Property>
        )
    }
}
