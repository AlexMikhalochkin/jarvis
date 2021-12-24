package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ShortCapability
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceInfo
import com.mega.demo.model.Device

/**
 * Verification for [DeviceToYandexDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToYandexDeviceConverterTest : BaseConverterTest<Device, YandexDevice>() {

    override fun getConverter() = DeviceToYandexDeviceConverter()

    override fun createSource() = createDevice()

    override fun createExpected(): YandexDevice {
        return YandexDevice(
            "свет на кухне",
            "devices.types.light",
            listOf(ShortCapability("devices.capabilities.on_off")),
            YandexDeviceInfo(
                "Provider2",
                "hue g11",
                "1.0",
                "1.0",
            ),
            externalDeviceId,
            "цветная лампа",
            "спальня",
            mapOf("port" to 7)
        )
    }
}
