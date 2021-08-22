package com.mega.demo.integration.api

import com.mega.demo.integration.PortStatusMessage

/**
 * Sender of [PortStatusMessage].
 *
 * @author Alex Mikhalochkin
 */
interface Sender {

    /**
     * Sends [message].
     */
    fun send(message: PortStatusMessage)
}
