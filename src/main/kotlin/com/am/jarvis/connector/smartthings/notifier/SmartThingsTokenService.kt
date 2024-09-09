package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.CallbackUrls
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

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

    private var grantCallbackAccessRequired = true
    private var callbackUrls: CallbackUrls? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null

    fun getAccessToken(): String {
        return requireNotNull(accessToken) { "SmartThings access token is not set" }
    }

    fun getCallbackUrl(): String {
        return requireNotNull(callbackUrls?.stateCallback) { "SmartThings callback URL is not set" }
    }

    fun isGrantCallbackAccessRequired(): Boolean {
        return grantCallbackAccessRequired
    }

    // todo rename. it stores token and urls
    fun storeCallbackToken(request: SmartThingsRequest) {
        callbackUrls = request.callbackUrls!!
        val convert: SmartThingsRequest = conversionService.convert(request, SmartThingsRequest::class.java)!!
        val tokenUrl = requireNotNull(callbackUrls?.oauthToken) { "SmartThings token URL is not set" }
        val callbackAuthentication = apiClient.getAccessToken(convert, tokenUrl)
        accessToken = callbackAuthentication?.accessToken!!
        refreshToken = callbackAuthentication.refreshToken!!
        val expiresIn = callbackAuthentication.expiresIn
        grantCallbackAccessRequired = false
    }
}
