package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.DeviceState
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
