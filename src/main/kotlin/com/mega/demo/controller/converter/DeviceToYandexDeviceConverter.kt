package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ShortCapability
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceInfo
import com.mega.demo.model.Provider
import com.mega.demo.model.SmartDevice
import com.mega.demo.model.TechnicalInfo
import com.mega.demo.model.yandex.Capability
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [SmartDevice] to [YandexDevice].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToYandexDeviceConverter : Converter<SmartDevice, YandexDevice> {

    override fun convert(source: SmartDevice): YandexDevice {
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

    private fun convertCapabilities(capabilities: List<Capability>?): List<ShortCapability> {
        return capabilities!!.map { ShortCapability(it.type) }
    }

    private fun convertDeviceInfo(deviceInfo: TechnicalInfo): YandexDeviceInfo {
        return YandexDeviceInfo(
            deviceInfo.manufacturer,
            deviceInfo.model,
            deviceInfo.hardwareVersion,
            deviceInfo.softwareVersion,
        )
    }
}
