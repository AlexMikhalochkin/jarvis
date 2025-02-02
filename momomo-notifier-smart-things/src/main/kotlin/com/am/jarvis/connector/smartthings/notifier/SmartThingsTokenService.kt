package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Service for getting SmartThings token.
 *
 * @author Alex Mikhalochkin.
 */
@Service
class SmartThingsTokenService(
    private val converter: Converter<String, SmartThingsRequest>,
    private val converter2: Converter<SmartThingsRequest, SmartThingsRequest>,
    private val apiClient: SmartThingsApiClient,
    @Value("\${smart-things.callback.refresh-token}") private val refreshToken: String,
    @Value("\${smart-things.callback.url}") private val callbackUrl: String,
    @Value("\${smart-things.callback.token-url}") private val oauthTokenUrl: String
) {

    private var token: Token = Token("stub", 0)

    fun getAccessToken(): String {
        if (token.isExpired()) {
            logger.info { "SmartThings. Refresh callback token." }
            val convert: SmartThingsRequest = converter.convert(refreshToken)!!
            val callbackAuthentication = apiClient.getAccessToken(convert, oauthTokenUrl)
            extracted(callbackAuthentication!!)
        }
        return token.accessToken
    }

    fun getCallbackUrl(): String {
        return callbackUrl
    }

    fun isGrantCallbackAccessRequired(): Boolean {
        logger.warn { "SmartThings. Grant callback access is required." }
        return false
    }

    fun storeCallbackTokenAndUrls(request: SmartThingsRequest) {
        val accessTokeRequest = converter2.convert(request)!!
        val callbackAuthentication = apiClient.getAccessToken(accessTokeRequest, oauthTokenUrl)
        callbackAuthentication?.refreshToken!!
        extracted(callbackAuthentication)
    }

    private fun extracted(callbackAuthentication: CallbackAuthentication) {
        val accessToken = callbackAuthentication.accessToken!!
        val expiresIn = callbackAuthentication.expiresIn
        token = Token(accessToken, expiresIn!!)
    }
}

@Suppress("MagicNumber")
data class Token(
    val accessToken: String,
    val expiresIn: Int,
    val validUntil: Long = System.currentTimeMillis() + expiresIn * 1000
) {

    fun isExpired(): Boolean = System.currentTimeMillis() > validUntil
}
