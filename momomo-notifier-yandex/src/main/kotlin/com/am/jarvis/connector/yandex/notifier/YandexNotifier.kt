package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.controller.generated.model.ChangeStatesDevice
import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.NotificationPayload
import com.am.jarvis.controller.generated.model.Property
import com.am.jarvis.controller.generated.model.YandexNotificationRequest
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Notifier] for Yandex.
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexNotifier(
    private val yandexApiClient: YandexApiClient,
    @Value("\${yandex.user-id}") private var userId: String
) : Notifier {

    override fun notify(states: List<DeviceState>, flag: Boolean) {
        logger.info { "Yandex. Send notification. Started. States=$states" }
        val changeStateDevice = if (flag) createRequest(states[0]) else createRequest2(states[0])
        val payload = NotificationPayload(userId, listOf(changeStateDevice))
        val requestPayload = YandexNotificationRequest(payload, getCurrentTimestamp())
        yandexApiClient.notify(requestPayload)
        logger.info { "Yandex. Send notification. Finished. States=$states, RequestPayload=$requestPayload" }
    }

    override fun getSource() = "YANDEX"

    private fun createRequest(state: DeviceState): ChangeStatesDevice {
        val fullCapability = FullCapability(
            YandexState("on", state.isOn),
            "devices.capabilities.on_off"
        )
        return ChangeStatesDevice(
            state.deviceId,
            listOf(fullCapability),
            null
        )
    }

    private fun createRequest2(state: DeviceState): ChangeStatesDevice {
        val property = createFloatProperty(state, "temperature", "temperature")
        val property2 = createFloatProperty(state, "humidity", "humidity")
        val property3 = createFloatProperty(state, "battery_level", "battery")
        val property4 = createFloatProperty(state, "voltage", "voltage")
        val property5 = createProperty(state, "button", "button", "devices.properties.event")
        return ChangeStatesDevice(
            state.deviceId,
            listOf(),
            listOfNotNull(property, property2, property3, property4, property5)
        )
    }

    private fun createProperty(state: DeviceState, instance: String, customData: String, type: String): Property? {
        val value = state.customData[customData] ?: return null
        val yandexState = YandexState(instance, value)
        return Property(yandexState, type)
    }

    private fun createFloatProperty(state: DeviceState, instance: String, customData: String): Property? {
        return createProperty(state, instance, customData, "devices.properties.float")
    }

    @Suppress("MagicNumber")
    private fun getCurrentTimestamp() = System.currentTimeMillis().div(1000)
}
