package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.AccessTokenResponse
import com.am.jarvis.controller.generated.model.CallbackUrls
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import mu.KotlinLogging
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

private val logger = KotlinLogging.logger {}

/**
 * Service for getting SmartThings token.
 *
 * @author Alex Mikhalochkin.
 */
@Service
class SmartThingsTokenService(
    private val conversionService: ConversionService
) {

    private val webClient = WebClient.create()
    private lateinit var callbackUrls: CallbackUrls
    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    private var grantCallbackAccessRequired = true

    @Suppress("FunctionOnlyReturningConstant", "ForbiddenComment")
    fun isGrantCallbackAccessRequired(): Boolean {
        return grantCallbackAccessRequired
    }

    // todo rename. it stores token and urls
    fun storeCallbackToken(request: SmartThingsRequest) {
        callbackUrls = request.callbackUrls!!
        val convert: SmartThingsRequest = conversionService.convert(request, SmartThingsRequest::class.java)!!
        getAccessToken(convert)
        grantCallbackAccessRequired = false
    }

    @Suppress("MagicNumber")
    private fun getAccessToken(request: SmartThingsRequest) {
        logger.info { "Get tokens for SmartThings. Started. RequestId=${request.headers.requestId}" }
        val callbackAuthentication = webClient.post()
            .uri(callbackUrls.oauthToken)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .map { it.callbackAuthentication }
            .block()
        accessToken = callbackAuthentication?.accessToken!!
        refreshToken = callbackAuthentication.refreshToken!!
        val expiresIn = callbackAuthentication.expiresIn
        logger.info {
            "Get tokens for SmartThings. Finished. AccessToken=${accessToken.slice(0..5)}," +
                " RefreshToken=${refreshToken.slice(0..5)}, ExpiresIn=$expiresIn"
        }
    }
}
