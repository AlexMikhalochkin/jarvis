package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.connector.smartthings.notifier.SmartThingsTokenService
import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.CallbackUrls
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Test class for [GrantCallbackAccessRequestHandler].
 *
 * @author Alex Mikhalochkin
 */
class GrantCallbackAccessRequestHandlerTest : BaseRequestHandlerTest<GrantCallbackAccessRequestHandler>() {

    @MockK(relaxUnitFun = true)
    lateinit var tokenService: SmartThingsTokenService

    @Test
    fun testHandleSmartThingsGrantCallbackAccess() {
        val smartThingsRequest = SmartThingsRequest(
            headers = Headers(interactionType = "grantCallbackAccess", requestId = requestId),
            authentication = Authentication(),
            callbackAuthentication = CallbackAuthentication(),
            callbackUrls = CallbackUrls("tokenUrl", "callbackUrl")
        )
        val response = handler.invoke(smartThingsRequest)

        verifyHeaders(response.body, "grantCallbackAccess")
        assertSame(HttpStatus.OK, response.statusCode)
        verify { tokenService.storeCallbackToken(smartThingsRequest) }
    }
}
