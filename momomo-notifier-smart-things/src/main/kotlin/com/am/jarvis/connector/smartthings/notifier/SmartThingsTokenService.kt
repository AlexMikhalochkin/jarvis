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

//    private var oauthTokenUrl: String? = null
//    private var callbackUrl: String? = null
//    private var refreshToken: String? = null
    private var accessToken: String? = null
    private var token: Token = Token("stub", 0)

    fun getAccessToken(): String {
        if (token.isExpired()) {
            logger.info { "SmartThings. Refresh callback token." }
            val tokenUrl = requireNotNull(oauthTokenUrl) { "SmartThings token URL is not set" }
            val convert: SmartThingsRequest = converter.convert(refreshToken)!!
            val callbackAuthentication = apiClient.getAccessToken(convert, tokenUrl)
            extracted(callbackAuthentication!!)
        }
        return token.accessToken
    }

    fun getCallbackUrl(): String {
        return requireNotNull(callbackUrl) { "SmartThings callback URL is not set" }
    }

    fun isGrantCallbackAccessRequired(): Boolean {
        val grantCallbackAccessRequired = null == refreshToken ||
            null == callbackUrl ||
            null == oauthTokenUrl
        if (grantCallbackAccessRequired) {
            logger.warn { "SmartThings. Grant callback access is required." }
        }
        return grantCallbackAccessRequired
    }

    // todo rename. it stores token and urls
    fun storeCallbackToken(request: SmartThingsRequest) {
//        oauthTokenUrl = request.callbackUrls?.oauthToken
//        callbackUrl = request.callbackUrls?.stateCallback
        val accessTokeRequest = converter2.convert(request)!!
        val callbackAuthentication = apiClient.getAccessToken(accessTokeRequest, oauthTokenUrl!!)
//        refreshToken = callbackAuthentication?.refreshToken!!
        callbackAuthentication?.refreshToken!!
        extracted(callbackAuthentication)
    }

    private fun extracted(callbackAuthentication: CallbackAuthentication) {
        accessToken = callbackAuthentication.accessToken!!
        val expiresIn = callbackAuthentication.expiresIn
        token = Token(accessToken!!, expiresIn!!)
    }
}

class Token(
    val accessToken: String,
    expiresIn: Int
) {

    private val validUntil = System.currentTimeMillis() + expiresIn * 1000

    fun isExpired(): Boolean = System.currentTimeMillis() > validUntil
}
