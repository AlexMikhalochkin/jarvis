package com.am.jarvis.connector.yandex.converter

import com.am.jarvis.controller.generated.model.ChangeStateDevice
import com.am.jarvis.core.model.DeviceState
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
            isOn = source.capabilities[0].state.value,
            customData = mapOf("port" to source.customData!!["port"] as Int)
        )
    }
}
