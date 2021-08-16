package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.controller.generated.model.ShortCapability
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceInfo
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.Device
import com.mega.demo.model.yandex.DeviceInfo
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [ChangeStateDevice] to [Device].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToYandexDeviceConverter : Converter<Device, YandexDevice> {

    override fun convert(source: Device): YandexDevice {
        return YandexDevice(
            source.name!!,
            source.type!!,
            convertCapabilities(source.capabilities),
            convertDeviceInfo(source.deviceInfo),
            source.id,
            source.description,
            source.room,
            source.customData
        )
    }

    private fun convertCapabilities(capabilities: List<Capability>?): List<ShortCapability> {
        return capabilities!!.map { ShortCapability(it.type) }
    }

    private fun convertDeviceInfo(deviceInfo: DeviceInfo?): YandexDeviceInfo {
        return YandexDeviceInfo(
            deviceInfo!!.manufacturer,
            deviceInfo.model,
            deviceInfo.hwVersion,
            deviceInfo.swVersion,
        )
    }
}
