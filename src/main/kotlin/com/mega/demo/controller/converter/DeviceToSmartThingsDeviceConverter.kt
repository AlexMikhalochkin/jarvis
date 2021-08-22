package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.DeviceContext
import com.mega.demo.controller.generated.model.ManufacturerInfo
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.Provider
import com.mega.demo.model.Device
import com.mega.demo.model.TechnicalInfo
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
            source.customData,
            source.name[Provider.SMART_THINGS],
            convertManufacturer(source.technicalInfo),
            convertContext(source),
            source.type[Provider.SMART_THINGS],
            source.id,
        )
    }

    private fun convertContext(deviceContext: Device): DeviceContext {
        return DeviceContext(
            deviceContext.room[Provider.SMART_THINGS],
            deviceContext.groups,
            deviceContext.categories,
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
