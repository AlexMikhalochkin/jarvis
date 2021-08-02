package com.mega.demo.controller.converter

import com.mega.demo.controller.model.smartthings.SmartThingsDevice
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
            source.manufacturerInfo,
            source.deviceContext,
            source.deviceHandlerType,
            source.deviceUniqueId,
        )
    }
}
