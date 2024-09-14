package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.SmartThingsCallbackState
import com.am.jarvis.controller.generated.model.SmartThingsDeviceState
import com.am.jarvis.core.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converter for SmartThings callback request.
 *
 * @author Alex Mikhalochkin.
 */
@Component
class SmartThingsConverter : Converter<DeviceState, SmartThingsDeviceState> {

    private val millisInSeconds = 1000

    override fun convert(source: DeviceState): SmartThingsDeviceState {
        val callbackState = SmartThingsCallbackState(
            timestamp = System.currentTimeMillis().div(millisInSeconds),
            value = source.stringState,
            capability = "st.switch",
            attribute = "switch"
        )
        return SmartThingsDeviceState(
            source.deviceId,
            listOf(callbackState)
        )
    }
}
