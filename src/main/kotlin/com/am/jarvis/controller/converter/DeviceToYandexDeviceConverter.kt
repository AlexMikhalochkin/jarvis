package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.ShortCapability
import com.am.jarvis.controller.generated.model.YandexDevice
import com.am.jarvis.controller.generated.model.YandexDeviceInfo
import com.am.momomo.model.Device
import com.am.momomo.model.TechnicalInfo
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [Device] to [YandexDevice].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToYandexDeviceConverter : Converter<Device, YandexDevice> {

    override fun convert(source: Device): YandexDevice {
        return YandexDevice(
            source.name.additionalName,
            "devices.types.light",
            convertCapabilities(source.capabilities),
            convertDeviceInfo(source.technicalInfo),
            source.id,
            source.description,
            source.room.additionalName,
            source.additionalData["port"]?.let { mapOf("port" to it) }
        )
    }

    private fun convertCapabilities(capabilities: List<String>?) = capabilities!!.map { ShortCapability(it) }

    private fun convertDeviceInfo(deviceInfo: TechnicalInfo): YandexDeviceInfo {
        return YandexDeviceInfo(
            deviceInfo.manufacturer,
            deviceInfo.model,
            deviceInfo.hardwareVersion,
            deviceInfo.softwareVersion,
        )
    }
}
