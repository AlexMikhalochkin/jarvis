package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.YandexState
import com.am.momomo.model.DeviceState
import com.am.momomo.notifier.api.Notifier
import com.fasterxml.jackson.annotation.JsonProperty
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Notifier] for Yandex.
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexNotifier(
    private val yandexWebClient: WebClient,
    @Value("\${yandex.token}") private var yandexToken: String,
    @Value("\${yandex.user-id}") private var userId: String
) : Notifier {

    override fun notify(states: List<DeviceState>) {
        logger.info { "Yandex. Send notification. Started. States=$states" }
        val requestPayload = createRequest(states[0])
        val bodyToMono = yandexWebClient.post()
            .header("Authorization", "OAuth $yandexToken")
            .bodyValue(requestPayload)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException::class.java) { ex -> Mono.just(ex.responseBodyAsString) }
            .block()
        logger.info { "Yandex. Send notification. Finished. Response=$bodyToMono" }
    }

    override fun getSource() = "YANDEX"

    private fun createRequest(state: DeviceState): YandexNotificationRequest {
        val fullCapability = FullCapability(
            YandexState("on", state.isOn),
            "devices.capabilities.on_off"
        )
        val changeStateDevice = ChangeStateDevice(
            state.deviceId,
            listOf(fullCapability)
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
    @field:JsonProperty("id") val id: String,
    @field:JsonProperty("capabilities")
    val capabilities: List<FullCapability>,
)
