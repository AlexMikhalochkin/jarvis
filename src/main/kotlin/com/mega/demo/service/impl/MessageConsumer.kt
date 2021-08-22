package com.mega.demo.service.impl

import com.mega.demo.integration.PortStatusMessage
import com.mega.demo.integration.api.PlcClient
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.JmsListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Consumer for [PortStatusMessage].
 *
 * @author Alex Mikhalochkin
 */
@Component
@Profile("consumer")
class MessageConsumer(val plcClient: PlcClient) {

    @JmsListener(destination = "message-queue")
    fun consume(@Payload portStatusMessage: PortStatusMessage) {
        logger.info { "Consume message. Started. Message=$portStatusMessage" }
        val port = portStatusMessage.port!!
        if (portStatusMessage.status!!) plcClient.turnOn(port) else plcClient.turnOn(port)
        logger.info { "Consume message. Finished. Message=$portStatusMessage" }
    }
}
