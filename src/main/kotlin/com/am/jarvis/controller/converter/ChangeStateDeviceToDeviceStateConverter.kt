package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.ChangeStateDevice
import com.am.momomo.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [ChangeStateDevice] to [DeviceState].
 *
 * @author Alex Mikhalochkin
 */
@Component
class ChangeStateDeviceToDeviceStateConverter : Converter<ChangeStateDevice, DeviceState> {

    override fun convert(source: ChangeStateDevice): DeviceState {
        return DeviceState(
            deviceId = source.id,
            port = source.customData!!["port"] as Int,
            isOn = source.capabilities[0].state.value
        )
    }
}
