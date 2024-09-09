package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.AccessTokenResponse
import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.SmartThingsCallbackRequest
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Component
class SmartThingsApiClient(
    private val smartThingsWebClient: WebClient
) {

    fun getAccessToken(request: SmartThingsRequest, tokenUrl: String): CallbackAuthentication? {
        val block = smartThingsWebClient.post()
            .uri(tokenUrl)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .map { it.callbackAuthentication }
            .block()
        return block
    }

    fun sendCallback(request: SmartThingsCallbackRequest, callbackUrl: String) {
        smartThingsWebClient.post()
            .uri(callbackUrl)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException::class.java) { ex -> Mono.just(ex.responseBodyAsString) }
            .block()
    }
}
