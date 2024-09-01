package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.momomo.model.DeviceState
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
            source.commands!![0].command!! == "on",
            mapOf("port" to source.deviceCookie!!["port"] as Int)
        )
    }
}
