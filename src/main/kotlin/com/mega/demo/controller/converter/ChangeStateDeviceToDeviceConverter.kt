package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.Device
import com.mega.demo.model.yandex.State
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts [ChangeStateDevice] to [Device].
 *
 * @author Alex Mikhalochkin
 */
@Component
class ChangeStateDeviceToDeviceConverter : Converter<ChangeStateDevice, Device> {

    override fun convert(source: ChangeStateDevice): Device {
        return Device(
            id = source.id,
            customData = source.customData,
            capabilities = convertCapabilities(source.capabilities)
        )
    }

    private fun convertCapabilities(capabilities: List<FullCapability>): List<Capability> =
        capabilities.map { Capability(it.type, convertState(it.state)) }

    private fun convertState(state: YandexState): State = State(state.instance, state.value)
}
