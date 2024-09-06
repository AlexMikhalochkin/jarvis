package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.DeviceContext
import com.am.jarvis.controller.generated.model.ManufacturerInfo
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.TechnicalInfo
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
            source.id,
            source.additionalData,
            source.name.primaryName,
            convertManufacturer(source.technicalInfo),
            convertContext(source),
            "c2c-switch",
            source.id,
        )
    }

    private fun convertContext(device: Device): DeviceContext {
        return DeviceContext(
            device.room.primaryName,
            device.groups,
            listOf("light", "switch")
        )
    }

    private fun convertManufacturer(technicalInfo: TechnicalInfo): ManufacturerInfo {
        return ManufacturerInfo(
            technicalInfo.manufacturer,
            technicalInfo.model,
            technicalInfo.hardwareVersion,
            technicalInfo.softwareVersion
        )
    }
}
