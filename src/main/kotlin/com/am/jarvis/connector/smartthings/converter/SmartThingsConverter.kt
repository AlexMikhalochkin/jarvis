package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.connector.smartthings.notifier.SmartThingsTokenService
import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsCallbackRequest
import com.am.jarvis.controller.generated.model.SmartThingsCallbackState
import com.am.jarvis.controller.generated.model.SmartThingsDeviceState
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.generateUuid
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converter for SmartThings callback request.
 *
 * @author Alex Mikhalochkin.
 */
@Component
class SmartThingsConverter(
    private val tokenService: SmartThingsTokenService
) : Converter<List<DeviceState>, SmartThingsCallbackRequest> {

    private val millisInSeconds = 1000

    override fun convert(source: List<DeviceState>): SmartThingsCallbackRequest {
        return SmartThingsCallbackRequest(
            Headers(
                interactionType = "stateCallback",
                requestId = generateUuid()
            ),
            Authentication(
                "Bearer",
                tokenService.getAccessToken()
            ),
            source.map { convert(it) }
        )
    }

    private fun convert(deviceState: DeviceState): SmartThingsDeviceState {
        return SmartThingsDeviceState(
            deviceState.deviceId,
            listOf(
                SmartThingsCallbackState(
                    System.currentTimeMillis().div(millisInSeconds),
                    deviceState.stringState,
                    "main",
                    "st.switch",
                    "switch"
                )
            )
        )
    }
}
