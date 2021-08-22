package com.mega.demo.integration.impl

import com.mega.demo.integration.PortStatusMessage
import com.mega.demo.integration.api.Sender
import mu.KotlinLogging
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [Sender].
 *
 * @author Alex Mikhalochkin
 */
@Service
class MessageSender(val jmsTemplate: JmsTemplate) : Sender {

    private val queue = "message-queue"

    override fun send(message: PortStatusMessage) {
        logger.info("Sending message. Started. Message=$message")
        jmsTemplate.convertAndSend(queue, message)
        logger.info("Sending message. Finished. Message=$message")
    }
}
