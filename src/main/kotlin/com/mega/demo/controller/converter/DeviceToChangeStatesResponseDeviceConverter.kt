package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ChangeStatesResponseCapabity
import com.mega.demo.controller.generated.model.ChangeStatesResponseDevice
import com.mega.demo.controller.generated.model.ChangeStatesResponseState
import com.mega.demo.model.yandex.ActionResult
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.DeviceStateHolder
import com.mega.demo.model.yandex.State
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [DeviceStateHolder] to [ChangeStatesResponseDevice].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToChangeStatesResponseDeviceConverter : Converter<DeviceStateHolder, ChangeStatesResponseDevice> {

    override fun convert(source: DeviceStateHolder): ChangeStatesResponseDevice {
        return ChangeStatesResponseDevice(
            id = source.id,
            capabilities = convertCapabilities(source.capabilities)
        )
    }

    private fun convertCapabilities(capabilities: List<Capability>?): List<ChangeStatesResponseCapabity> {
        return capabilities!!.map { ChangeStatesResponseCapabity(it.type, convertState(it.state)) }
    }

    private fun convertState(state: State?): ChangeStatesResponseState {
        return ChangeStatesResponseState(
            state!!.instance,
            convertActionResult(state.actionResult)
        )
    }

    private fun convertActionResult(actionResult: ActionResult?):
            com.mega.demo.controller.generated.model.ActionResult {
        return com.mega.demo.controller.generated.model.ActionResult(
            actionResult!!.status
        )
    }
}
