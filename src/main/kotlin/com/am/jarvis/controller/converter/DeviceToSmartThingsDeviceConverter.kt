package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.DeviceContext
import com.am.jarvis.controller.generated.model.ManufacturerInfo
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.momomo.model.Device
import com.am.momomo.model.Provider
import com.am.momomo.model.TechnicalInfo
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
            mapOf("port" to source.port),
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
