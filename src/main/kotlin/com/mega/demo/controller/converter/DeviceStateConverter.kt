package com.mega.demo.controller.converter

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.State
import com.mega.demo.model.DeviceStatus
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceStatus] to [DeviceState].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceStateConverter : Converter<DeviceStatus, DeviceState> {

    override fun convert(source: DeviceStatus): DeviceState {
        val isOn: String = if (source.status) "on" else "off"
        val state = State("main", "st.switch", "switch", isOn)
        return DeviceState(source.externalDeviceId, listOf(state))
    }
}
