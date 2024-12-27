package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsCallbackRequest
import com.am.jarvis.controller.generated.model.SmartThingsDeviceState
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.UUID

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Notifier] for Samsung Smart Things.
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsNotifier(
    private val smartThingsApiClient: SmartThingsApiClient,
    private val tokenService: SmartThingsTokenService,
    private val converter: Converter<DeviceState, SmartThingsDeviceState>
) : Notifier {

    override fun notify(states: List<DeviceState>, flag: Boolean) {
        logger.info { "SmartThings. Send notification. Started. States=$states" }
        val requestPayload = SmartThingsCallbackRequest(
            Headers(
                interactionType = "stateCallback",
                requestId = UUID.randomUUID().toString()
            ),
            Authentication(
                "Bearer",
                tokenService.getAccessToken()
            ),
            states.mapNotNull { converter.convert(it) }
        )
        val callbackUrl = tokenService.getCallbackUrl()
        smartThingsApiClient.sendCallback(requestPayload, callbackUrl)
        logger.info { "SmartThings. Send notification. Finished. States=$states" }
    }

    override fun getSource(): String {
        return "SMART_THINGS"
    }
}
