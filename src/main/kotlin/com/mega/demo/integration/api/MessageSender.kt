package com.mega.demo.integration.api

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
