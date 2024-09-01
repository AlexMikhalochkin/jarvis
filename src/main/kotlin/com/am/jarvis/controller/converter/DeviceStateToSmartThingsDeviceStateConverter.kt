package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.State
import com.am.momomo.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceState] to [com.am.jarvis.controller.generated.model.DeviceState].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceStateToSmartThingsDeviceStateConverter :
    Converter<DeviceState, com.am.jarvis.controller.generated.model.DeviceState> {

    override fun convert(source: DeviceState): com.am.jarvis.controller.generated.model.DeviceState {
        return deviceState(source.deviceId, source.stringState)
    }

    private fun deviceState(
        deviceId: String,
        portStatus: String
    ): com.am.jarvis.controller.generated.model.DeviceState {
        val state = State(capability = "st.switch", attribute = "switch", value = portStatus)
        return com.am.jarvis.controller.generated.model.DeviceState(deviceId, emptyMap(), listOf(state))
    }
}
