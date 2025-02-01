package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.controller.generated.model.ChangeStatesDevice
import com.am.jarvis.controller.generated.model.NotificationPayload
import com.am.jarvis.controller.generated.model.YandexNotificationRequest
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Notifier] for Yandex.
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexNotifier(
    private val yandexApiClient: YandexApiClient,
    private val stateConverter: Converter<DeviceState, ChangeStatesDevice>,
    @Value("\${yandex.user-id}") private var userId: String
) : Notifier {

    override fun notify(states: List<DeviceState>) {
        logger.info { "Yandex. Send notification. Started. States=$states" }
        val yandexState = stateConverter.convert(states[0])
        val payload = NotificationPayload(userId, listOfNotNull(yandexState))
        val requestPayload = YandexNotificationRequest(payload, getCurrentTimestamp())
        yandexApiClient.notify(requestPayload)
        logger.info { "Yandex. Send notification. Finished. States=$states, RequestPayload=$requestPayload" }
    }

    override fun getSource() = "YANDEX"

    @Suppress("MagicNumber")
    private fun getCurrentTimestamp() = System.currentTimeMillis().div(1000)
}
