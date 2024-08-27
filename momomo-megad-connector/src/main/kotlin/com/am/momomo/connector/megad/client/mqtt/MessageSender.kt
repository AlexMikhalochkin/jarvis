package com.am.momomo.connector.megad.client.mqtt

/**
 * Sender of messages.
 *
 * @author Alex Mikhalochkin
 */
interface MessageSender {

    /**
     * Sends [message].
     */
    fun send(message: String)
}
