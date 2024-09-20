package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import mu.KotlinLogging
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Service for getting SmartThings token.
 *
 * @author Alex Mikhalochkin.
 */
@Service
class SmartThingsTokenService(
    private val conversionService: ConversionService,
    private val apiClient: SmartThingsApiClient
) {

    private var oauthTokenUrl: String? = null
    private var callbackUrl: String? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var token: Token = Token("stub", 0)

    fun getAccessToken(): String {
        if (token.isExpired()) {
            logger.info { "SmartThings. Refresh callback token." }
            val tokenUrl = requireNotNull(oauthTokenUrl) { "SmartThings token URL is not set" }
            val convert: SmartThingsRequest = conversionService.convert(refreshToken, SmartThingsRequest::class.java)!!
            val callbackAuthentication = apiClient.getAccessToken(convert, tokenUrl)
            extracted(callbackAuthentication!!)
        }
        return token.accessToken
    }

    fun getCallbackUrl(): String {
        return requireNotNull(callbackUrl) { "SmartThings callback URL is not set" }
    }

    fun isGrantCallbackAccessRequired(): Boolean {
        val grantCallbackAccessRequired = null == refreshToken
            || null == callbackUrl
            || null == oauthTokenUrl
        if (grantCallbackAccessRequired) {
            logger.warn { "SmartThings. Grant callback access is required." }
        }
        return grantCallbackAccessRequired
    }

    // todo rename. it stores token and urls
    fun storeCallbackToken(request: SmartThingsRequest) {
        oauthTokenUrl = request.callbackUrls?.oauthToken
        callbackUrl = request.callbackUrls?.stateCallback
        val accessTokeRequest = conversionService.convert(request, SmartThingsRequest::class.java)!!
        val callbackAuthentication = apiClient.getAccessToken(accessTokeRequest, oauthTokenUrl!!)
        refreshToken = callbackAuthentication?.refreshToken!!
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
