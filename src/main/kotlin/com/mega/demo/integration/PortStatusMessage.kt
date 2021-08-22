package com.mega.demo.integration

/**
 * Message with port status.
 *
 * @author Alex Mikhalochkin
 */
data class PortStatusMessage(
    val port: Int? = null,
    val status: Boolean? = null
)
