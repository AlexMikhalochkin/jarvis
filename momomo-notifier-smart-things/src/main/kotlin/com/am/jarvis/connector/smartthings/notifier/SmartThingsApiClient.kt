package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.AccessTokenResponse
import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.SmartThingsCallbackRequest
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.core.model.RetryableServerException
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.RetryBackoffSpec
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

private val logger = KotlinLogging.logger {}

/**
 * Client for SmartThings API.
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsApiClient(
    private val webClient: WebClient,
    private val retryBackoffSpec: RetryBackoffSpec,
) {

    fun getAccessToken(request: SmartThingsRequest, tokenUrl: String): CallbackAuthentication? {
        val block = webClient.post()
            .uri(tokenUrl)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .map { it.callbackAuthentication }
            .block()
        return block
    }

    fun sendCallback(request: SmartThingsCallbackRequest, callbackUrl: String) {
        webClient.post()
            .uri(callbackUrl)
            .bodyValue(request)
            .retrieve()
            .onStatus({ it.is5xxServerError }, { _ -> Mono.error(RetryableServerException()) })
            .bodyToMono(String::class.java)
            .timeout(5.seconds.toJavaDuration())
            .retryWhen(retryBackoffSpec)
            .doOnError { logger.error(it) { "SmartThings notification Error. Request=$request" } }
            .block()
    }
}
