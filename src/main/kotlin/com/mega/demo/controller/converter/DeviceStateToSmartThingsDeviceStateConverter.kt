package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.State
import com.mega.demo.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceState] to [com.mega.demo.controller.generated.model.DeviceState].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceStateToSmartThingsDeviceStateConverter :
    Converter<DeviceState, com.mega.demo.controller.generated.model.DeviceState> {

    override fun convert(source: DeviceState): com.mega.demo.controller.generated.model.DeviceState {
        return deviceState(source.deviceId!!, if (source.isOn!!) "on" else "off")
    }

    private fun deviceState(
        deviceId: String,
        portStatus: String
    ): com.mega.demo.controller.generated.model.DeviceState {
        val state = State(capability = "st.switch", attribute = "switch", value = portStatus)
        return com.mega.demo.controller.generated.model.DeviceState(deviceId, emptyMap(), listOf(state))
    }
}
