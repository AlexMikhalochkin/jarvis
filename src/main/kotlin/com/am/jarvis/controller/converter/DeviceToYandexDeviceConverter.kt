package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.ShortCapability
import com.am.jarvis.controller.generated.model.YandexDevice
import com.am.jarvis.controller.generated.model.YandexDeviceInfo
import com.am.jarvis.model.Device
import com.am.jarvis.model.Provider
import com.am.jarvis.model.TechnicalInfo
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
            mapOf("port" to source.port)
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
