package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ActionResult
import com.mega.demo.controller.generated.model.ChangeStatesResponseCapability
import com.mega.demo.controller.generated.model.ChangeStatesResponseDevice
import com.mega.demo.controller.generated.model.ChangeStatesResponseState
import com.mega.demo.model.DeviceState
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
