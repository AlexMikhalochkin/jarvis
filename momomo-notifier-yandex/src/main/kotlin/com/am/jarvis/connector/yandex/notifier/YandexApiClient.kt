package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.controller.generated.model.YandexNotificationRequest
import com.am.jarvis.core.model.RetryableServerException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.RetryBackoffSpec

private val logger = KotlinLogging.logger {}

/**
 * Client for Yandex API.
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexApiClient(
    private val webClient: WebClient,
    private val retryBackoffSpec: RetryBackoffSpec,
    @Value("\${yandex.token}") private val yandexToken: String,
    @Value("\${yandex.notification-url}") private val yandexUrl: String
) {

    fun notify(requestPayload: YandexNotificationRequest) {
        webClient.post()
            .uri(yandexUrl)
            .header("Authorization", "OAuth $yandexToken")
            .bodyValue(requestPayload)
            .retrieve()
            .onStatus({ it.is5xxServerError }, { _ -> Mono.error(RetryableServerException()) })
            .bodyToMono(String::class.java)
            .retryWhen(retryBackoffSpec)
            .doOnError { logger.error(it) { "Error while sending notification to Yandex" } }
            .block()
    }
}
