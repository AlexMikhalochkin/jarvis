package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.ActionResult
import com.am.jarvis.controller.generated.model.ChangeStatesResponseCapability
import com.am.jarvis.controller.generated.model.ChangeStatesResponseDevice
import com.am.jarvis.controller.generated.model.ChangeStatesResponseState
import com.am.jarvis.model.DeviceState
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceState] to [ChangeStatesResponseDevice].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToChangeStatesResponseDeviceConverter : Converter<DeviceState, ChangeStatesResponseDevice> {

    override fun convert(source: DeviceState): ChangeStatesResponseDevice {
        return ChangeStatesResponseDevice(
            id = source.deviceId,
            capabilities = convertCapabilities()
        )
    }

    private fun convertCapabilities(): List<ChangeStatesResponseCapability> {
        return listOf(ChangeStatesResponseCapability("devices.capabilities.on_off", convertState()))
    }

    private fun convertState(): ChangeStatesResponseState {
        return ChangeStatesResponseState(
            "on",
            ActionResult("DONE")
        )
    }
}
