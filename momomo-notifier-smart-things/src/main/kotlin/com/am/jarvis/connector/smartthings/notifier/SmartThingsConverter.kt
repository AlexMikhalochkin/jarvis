package com.am.jarvis.connector.smartthings.notifier

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
        val states = if (source.customData.containsKey("humidity")) {
            smartThingsCallbackStates(source)
        } else {
            smartThingsCallbackStates2(source)
        }
        return SmartThingsDeviceState(
            source.deviceId,
            states
        )
    }

    private fun smartThingsCallbackStates2(source: DeviceState): List<SmartThingsCallbackState> {
        val callbackState = smartThingsCallbackState(source.stringState, "st.switch", "switch")
        return listOf(callbackState)
    }

    private fun smartThingsCallbackStates(source: DeviceState): List<SmartThingsCallbackState> {
        val callbackState = smartThingsCallbackState(
            source.customData.getValue("humidity"),
            "st.relativeHumidityMeasurement",
            "humidity"
        )
        val callbackState2 = smartThingsCallbackState(source.customData.getValue("battery"), "st.battery", "battery")
        val callbackState3 = smartThingsCallbackState(
            source.customData.getValue("temperature"),
            "st.temperatureMeasurement",
            "temperature",
            "C"
        )
        return listOf(callbackState, callbackState2, callbackState3)
    }

    private fun smartThingsCallbackState(
        value: Any,
        capability: String,
        attribute: String,
        unit: String? = null
    ): SmartThingsCallbackState {
        return SmartThingsCallbackState(
            timestamp = System.currentTimeMillis().div(millisInSeconds),
            value = value,
            capability = capability,
            unit = unit,
            attribute = attribute
        )
    }
}
