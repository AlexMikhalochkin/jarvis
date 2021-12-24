package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.DeviceContext
import com.mega.demo.controller.generated.model.ManufacturerInfo
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.Device

/**
 * Verification for [DeviceToSmartThingsDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToSmartThingsDeviceConverterTest : BaseConverterTest<Device, SmartThingsDevice>() {

    override fun getConverter() = DeviceToSmartThingsDeviceConverter()

    override fun createExpected(): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            mapOf("port" to 7),
            "friendly name",
            ManufacturerInfo(
                "Provider2",
                "hue g11",
                "1.0",
                "1.0"
            ),
            DeviceContext(
                "Kitchen",
                listOf("Kitchen Lights", "House Bulbs"),
                listOf("light", "switch")
            ),
            "handler type",
            externalDeviceId
        )
    }

    override fun createSource() = createDevice()
}
