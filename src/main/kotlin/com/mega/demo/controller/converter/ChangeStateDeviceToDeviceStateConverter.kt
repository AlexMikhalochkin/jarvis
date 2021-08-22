package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.model.DeviceState
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
