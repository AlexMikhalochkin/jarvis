package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ShortCapability
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceInfo
import com.mega.demo.model.Device
import com.mega.demo.model.Provider
import com.mega.demo.model.TechnicalInfo
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
            source.name.getValue(Provider.YANDEX),
            source.type.getValue(Provider.YANDEX),
            convertCapabilities(source.capabilities),
            convertDeviceInfo(source.technicalInfo),
            source.id,
            source.description,
            source.room.getValue(Provider.YANDEX),
            source.customData
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
