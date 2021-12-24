package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ActionResult
import com.mega.demo.controller.generated.model.ChangeStatesResponseCapability
import com.mega.demo.controller.generated.model.ChangeStatesResponseDevice
import com.mega.demo.controller.generated.model.ChangeStatesResponseState
import com.mega.demo.model.DeviceState

/**
 * Verification for [DeviceToChangeStatesResponseDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToChangeStatesResponseDeviceConverterTest :
    BaseConverterTest<DeviceState, ChangeStatesResponseDevice>() {

    override fun getConverter() = DeviceToChangeStatesResponseDeviceConverter()

    override fun createSource() = DeviceState("id", 1, true)

    override fun createExpected(): ChangeStatesResponseDevice {
        val state = ChangeStatesResponseState(
            "on",
            ActionResult("DONE")
        )
        val capability = ChangeStatesResponseCapability("devices.capabilities.on_off", state)
        return ChangeStatesResponseDevice(
            id = "id",
            capabilities = listOf(capability)
        )
    }
}
