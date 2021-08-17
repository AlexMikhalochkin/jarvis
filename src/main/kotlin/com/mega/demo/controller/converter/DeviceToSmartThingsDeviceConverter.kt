package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.DeviceContext
import com.mega.demo.controller.generated.model.ManufacturerInfo
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.Device
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [Device] to [SmartThingsDevice].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToSmartThingsDeviceConverter : Converter<Device, SmartThingsDevice> {

    override fun convert(source: Device): SmartThingsDevice {
        return SmartThingsDevice(
            source.externalDeviceId,
            source.deviceCookie,
            source.friendlyName,
            convertManufacturer(source.manufacturerInfo),
            convertContext(source.deviceContext),
            source.deviceHandlerType,
            source.deviceUniqueId,
        )
    }

    private fun convertContext(deviceContext: Map<String, Any>): DeviceContext {
        return DeviceContext(
            deviceContext["roomName"].toString(),
            deviceContext["groups"] as List<String>,
            deviceContext["categories"] as List<String>,
        )
    }

    private fun convertManufacturer(manufacturerInfo: Map<String, String>): ManufacturerInfo {
        return ManufacturerInfo(
            manufacturerInfo["manufacturerName"].toString(),
            manufacturerInfo["modelName"].toString(),
            manufacturerInfo["hwVersion"].toString(),
            manufacturerInfo["swVersion"].toString()
        )
    }
}
