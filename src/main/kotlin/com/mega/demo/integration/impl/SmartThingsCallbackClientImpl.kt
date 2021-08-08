package com.mega.demo.integration.impl

import com.mega.demo.controller.model.smartthings.Authentication
import com.mega.demo.controller.model.smartthings.CallbackAuthentication
import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.Headers
import com.mega.demo.controller.model.smartthings.SmartThingsRequest
import com.mega.demo.generateUuid
import com.mega.demo.integration.api.SmartThingsCallbackClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.reactive.function.client.WebClient

/**
 * Implementation of [SmartThingsCallbackClient].
 *
 * @author Alex Mikhalochkin
 */
class SmartThingsCallbackClientImpl(@Qualifier("smartThingsWebClient") val webClient: WebClient) :
    SmartThingsCallbackClient {

    @Value("{\$smart.things.client.secret}")
    private lateinit var clientSecret: String

    @Value("{\$smart.things.client.id}")
    private lateinit var clientId: String

    override fun getToken(authenticationCode: String): String {
        val headers = Headers("accessTokenRequest", generateUuid())
        val callbackAuthentication = CallbackAuthentication(
            "authorization_code",
            authenticationCode,
            clientId,
            clientSecret
        )
        return webClient.post()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/oauth/token")
                    .build()
            }
            .bodyValue(SmartThingsRequest(headers, callbackAuthentication))
            .retrieve()
            .bodyToMono(CallbackAuthentication::class.java)
            .block()!!
            .accessToken!!
    }

    override fun pushStates(deviceStates: List<DeviceState>) {
        val headers = Headers("stateCallback", generateUuid())
        val authentication = Authentication("TODO token")
        val request =
            SmartThingsRequest(headers, authentication = authentication, deviceState = deviceStates)
        webClient.post()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/device/events")
                    .build()
            }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void::class.java)
            .block()
    }
}
