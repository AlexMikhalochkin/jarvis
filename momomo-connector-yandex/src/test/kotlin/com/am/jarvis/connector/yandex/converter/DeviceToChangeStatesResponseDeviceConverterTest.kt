package com.am.jarvis.connector.yandex.converter

import com.am.jarvis.controller.generated.model.ActionResult
import com.am.jarvis.controller.generated.model.ChangeStatesResponseCapability
import com.am.jarvis.controller.generated.model.ChangeStatesResponseDevice
import com.am.jarvis.controller.generated.model.ChangeStatesResponseState
import com.am.jarvis.core.model.DeviceState

/**
 * Verification for [DeviceToChangeStatesResponseDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToChangeStatesResponseDeviceConverterTest :
    BaseConverterTest<DeviceState, ChangeStatesResponseDevice>() {

    override fun getConverter() = DeviceToChangeStatesResponseDeviceConverter()

    override fun createSource() = DeviceState("id", true, mapOf("port" to 1))

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
