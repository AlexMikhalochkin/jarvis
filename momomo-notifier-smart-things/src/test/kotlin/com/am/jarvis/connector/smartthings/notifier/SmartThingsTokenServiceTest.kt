package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.converter.Converter

/**
 * Verification of the [SmartThingsTokenService]
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class SmartThingsTokenServiceTest {

    private val refreshToken = "refresh-token"
    private val callbackUrl = "http://callback-url.com"
    private val oauthTokenUrl = "http:/oauth-token-url.com"

    @MockK
    private lateinit var converter: Converter<String, SmartThingsRequest>

    @MockK
    private lateinit var converter2: Converter<SmartThingsRequest, SmartThingsRequest>

    @MockK
    private lateinit var apiClient: SmartThingsApiClient

    private lateinit var tokenService: SmartThingsTokenService

    @BeforeEach
    fun init() {
        tokenService = SmartThingsTokenService(
            converter,
            converter2,
            apiClient,
            refreshToken,
            callbackUrl,
            oauthTokenUrl
        )
    }

    @Test
    fun getAccessToken() {
        val smartThingsRequest = SmartThingsRequest(
            Headers(interactionType = "interactionType", requestId = "requestId"),
            Authentication()
        )
        val accessToken = "accessToken"
        val callbackAuthentication = CallbackAuthentication(accessToken = accessToken, expiresIn = 10)
        every { converter.convert(refreshToken) } returns smartThingsRequest
        every { apiClient.getAccessToken(smartThingsRequest, oauthTokenUrl) } returns callbackAuthentication

        val result = tokenService.getAccessToken()

        assertSame(accessToken, result)
        verify { converter2 wasNot Called }
    }

    @Test
    fun getCallbackUrl() {
        val result = tokenService.getCallbackUrl()

        assertSame(callbackUrl, result)

        verify { apiClient wasNot Called }
        verify { converter wasNot Called }
        verify { converter2 wasNot Called }
    }

    @Test
    fun isGrantCallbackAccessRequired() {
        assertFalse(tokenService.isGrantCallbackAccessRequired())

        verify { apiClient wasNot Called }
        verify { converter wasNot Called }
        verify { converter2 wasNot Called }
    }

    @Test
    fun storeCallbackTokenAndUrls() {
        val smartThingsRequest = SmartThingsRequest(
            Headers(interactionType = "interactionType", requestId = "requestId"),
            Authentication()
        )
        val callbackAuthentication = CallbackAuthentication(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            expiresIn = 10
        )
        every { converter2.convert(smartThingsRequest) } returns smartThingsRequest
        every { apiClient.getAccessToken(smartThingsRequest, oauthTokenUrl) } returns callbackAuthentication

        tokenService.storeCallbackTokenAndUrls(smartThingsRequest)

        verify { converter wasNot Called }
    }
}
