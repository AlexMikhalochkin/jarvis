package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [SmartThingsDevice] to [DeviceState].
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsDeviceToDeviceStateConverter : Converter<SmartThingsDevice, DeviceState> {

    override fun convert(source: SmartThingsDevice): DeviceState {
        return DeviceState(
            source.externalDeviceId!!,
            source.deviceCookie!!["port"] as Int,
            source.commands!![0].command!! == "on"
        )
    }
}
