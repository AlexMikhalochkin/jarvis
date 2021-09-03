package com.mega.demo.integration.impl

import com.fasterxml.jackson.annotation.JsonProperty
import com.mega.demo.controller.generated.model.ChangeStateDevice
import com.mega.demo.controller.generated.model.FullCapability
import com.mega.demo.controller.generated.model.YandexState
import com.mega.demo.integration.api.YandexCallbackClient
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

@Component
class YandexCallbackClientImpl(val yandexWebClient: WebClient) : YandexCallbackClient {

    private val millisInSecond = 1000

    @Value("\${yandex.token}")
    private lateinit var yandexToken: String

    override fun send(deviceId: String, isOn: Boolean) {
        logger.info { "Yandex. Send notification. Started" }
        val requestPayload = createRequest(deviceId, isOn)
        val bodyToMono = yandexWebClient.post()
            .header("Authorization", "OAuth $yandexToken")
            .bodyValue(requestPayload)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException::class.java) { ex -> Mono.just(ex.responseBodyAsString) }
            .block()
        logger.info { "Yandex. Send notification. Finished. Response=$bodyToMono" }
    }

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
