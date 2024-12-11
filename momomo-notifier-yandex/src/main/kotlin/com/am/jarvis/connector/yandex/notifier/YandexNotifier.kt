package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.YandexState
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import com.fasterxml.jackson.annotation.JsonProperty
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
        val changeStateDevice = ChangeStateDevice(
            state.deviceId,
            listOf(fullCapability),
            null
        )
        val payload = Payload(
            userId,
            listOf(changeStateDevice)
        )
        return YandexNotificationRequest(payload)
    }

    private fun createRequest2(state: DeviceState): YandexNotificationRequest {
        val property = Property(
            YandexState2("temperature", (state.customData["temperature"] as Int).toFloat()),
            "devices.properties.float"
        )
        val property2 = Property(
            YandexState2("humidity", (state.customData["humidity"] as Int).toFloat()),
            "devices.properties.float"
        )
        val property3 = Property(
            YandexState2("battery_level", (state.customData["battery"] as Int).toFloat()),
            "devices.properties.float"
        )
        val changeStateDevice = ChangeStateDevice(
            state.deviceId,
            listOf(),
            listOf(property, property2, property3)
        )
        val payload = Payload(
            userId,
            listOf(changeStateDevice)
        )
        return YandexNotificationRequest(payload)
    }
}

data class YandexNotificationRequest(
    val payload: Payload,
    @Suppress("MagicNumber")
    val ts: Long = System.currentTimeMillis().div(1000)
)

data class Payload(
    @field:JsonProperty("user_id", required = true) val userId: String,
    val devices: List<ChangeStateDevice>
)

data class ChangeStateDevice(
    @field:JsonProperty("id")
    val id: String,
    @field:JsonProperty("capabilities")
    val capabilities: List<FullCapability>,
    @field:JsonProperty("properties")
    val properties: List<Property>?,
)

data class Property(
    @field:JsonProperty("state")
    val state: YandexState2,
    @field:JsonProperty("type")
    val type: String
)

data class YandexState2(
    @field:JsonProperty("instance")
    val instance: String,
    @field:JsonProperty("value")
    val float: Float
)
