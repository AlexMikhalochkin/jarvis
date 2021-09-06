package com.mega.demo.integration.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.integration.api.SmartHomeProviderClient
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [SmartHomeProviderClient] for Yandex.
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexClient(val yandexWebClient: WebClient) : SmartHomeProviderClient {

    private val millisInSecond = 1000

    @Value("\${yandex.token}")
    private lateinit var yandexToken: String

    override fun updateStates(states: List<DeviceState>) {
        logger.info { "Yandex. Send notification. Started" }
        val requestPayload = createRequest(states[0].deviceId!!, states[0].isOn!!)
        val bodyToMono = yandexWebClient.post()
            .header("Authorization", "OAuth $yandexToken")
            .bodyValue(requestPayload)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException::class.java) { ex -> Mono.just(ex.responseBodyAsString) }
            .block()
        logger.info { "Yandex. Send notification. Finished. Response=$bodyToMono" }
    }

    override fun getProvider() = Provider.YANDEX

    private fun createRequest(deviceId: String, isOn: Boolean): YandexNotificationRequest {
        val changeStateDevice = ChangeStateDevice(
            deviceId,
            listOf(FullCapability(YandexState("on", isOn), "devices.capabilities.on_off"))
        )
        val payload = Payload("user-id", listOf(changeStateDevice))
        return YandexNotificationRequest(
            System.currentTimeMillis().div(millisInSecond),
            payload
        )
    }
}

data class YandexNotificationRequest(
    val ts: Long,
    val payload: Payload
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
