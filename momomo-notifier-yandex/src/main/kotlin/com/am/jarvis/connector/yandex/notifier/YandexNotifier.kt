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
        val requestPayload = if (flag) createRequest(states[0]) else createRequest2(states[0])
        yandexApiClient.notify(requestPayload)
        logger.info { "Yandex. Send notification. Finished. States=$states, RequestPayload=$requestPayload" }
    }

    override fun getSource() = "YANDEX"

    private fun createRequest(state: DeviceState): YandexNotificationRequest {
        val fullCapability = FullCapability(
            YandexState("on", state.isOn),
            "devices.capabilities.on_off"
        )
        val changeStateDevice = ChangeStatesDevice(
            state.deviceId,
            listOf(fullCapability),
            null
        )
        val payload = NotificationPayload(
            userId,
            listOf(changeStateDevice)
        )
        return YandexNotificationRequest(payload, getCurrentTimestamp())
    }

    private fun createRequest2(state: DeviceState): YandexNotificationRequest {
        val property = property(state, "temperature", "temperature")
        val property2 = property(state, "humidity", "humidity")
        val property3 = property(state, "battery_level", "battery")
        val property4 = property(state, "voltage", "voltage")
        val property5 = property2(state)
        val changeStateDevice = ChangeStatesDevice(
            state.deviceId,
            listOf(),
            listOfNotNull(property, property2, property3, property4, property5)
        )
        val payload = NotificationPayload(
            userId,
            listOf(changeStateDevice)
        )
        return YandexNotificationRequest(payload, getCurrentTimestamp())
    }

    private fun property(state: DeviceState, instance: String, customData: String): Property? {
        val any = state.customData[customData] ?: return null
        val float = (any as Int).toFloat()
        return Property(
            YandexState(instance, float),
            "devices.properties.float"
        )
    }

    private fun property2(state: DeviceState): Property? {
        val any = state.customData["button"] ?: return null
        val float = any as String
        return Property(
            YandexState("button", float),
            "devices.properties.event"
        )
    }

    @Suppress("MagicNumber")
    private fun getCurrentTimestamp() = System.currentTimeMillis().div(1000)
}
