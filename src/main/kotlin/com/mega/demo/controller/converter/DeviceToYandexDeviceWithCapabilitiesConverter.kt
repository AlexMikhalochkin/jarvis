package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexDeviceWithCapabilities
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.Device
import com.mega.demo.model.yandex.State
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [Device] to [YandexDeviceWithCapabilities].
 *
 * @author Alex Mikhalochkin
 */
@Component
class DeviceToYandexDeviceWithCapabilitiesConverter : Converter<Device, YandexDeviceWithCapabilities> {

    override fun convert(source: Device): YandexDeviceWithCapabilities {
        return YandexDeviceWithCapabilities(
            source.id,
            convertCapabilities(source.capabilities)
        )
    }

    private fun convertCapabilities(capabilities: List<Capability>?): List<FullCapability> {
        return capabilities!!.map { FullCapability(convertState(it.state), it.type) }
    }

    private fun convertState(state: State?): YandexState {
        return YandexState(
            state!!.instance,
            state.value as Boolean
        )
    }
}
