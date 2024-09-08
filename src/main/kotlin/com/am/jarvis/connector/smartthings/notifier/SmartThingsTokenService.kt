package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.CallbackUrls
import org.springframework.stereotype.Service

/**
 * Service for getting SmartThings token.
 *
 * @author Alex Mikhalochkin.
 */
@Service
class SmartThingsTokenService {

    @Suppress("FunctionOnlyReturningConstant", "ForbiddenComment")
    fun isGrantCallbackAccessRequired(): Boolean {
        // TODO: Implement checking if grant callback access required
        return false
    }

    @Suppress("UnusedParameter", "ForbiddenComment")
    fun storeCallbackToken(
        callbackAuthentication: CallbackAuthentication,
        callbackUrls: CallbackUrls
    ) {
        // TODO: Implement storing callback token
    }
}
