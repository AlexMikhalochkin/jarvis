package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Notifier] for Samsung Smart Things.
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsNotifier : Notifier {

    override fun notify(states: List<DeviceState>) {
        logger.info { "Smart Things notifier is not implemented yet. States: $states" }
    }

    override fun getSource(): String {
        return "SMART_THINGS"
    }
}
