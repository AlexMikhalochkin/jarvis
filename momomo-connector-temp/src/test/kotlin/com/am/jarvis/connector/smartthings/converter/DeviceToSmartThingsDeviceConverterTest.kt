package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.connector.BaseConverterTest
import com.am.jarvis.controller.generated.model.DeviceContext
import com.am.jarvis.controller.generated.model.ManufacturerInfo
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.Device

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
            "c2c-switch",
            externalDeviceId
        )
    }

    override fun createSource() = createDevice()
}
