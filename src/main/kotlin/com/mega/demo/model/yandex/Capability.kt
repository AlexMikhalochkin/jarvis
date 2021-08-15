package com.mega.demo.model.yandex

/**
 * Represents capability of the device.
 *
 * @author Alex Mikhalochkin
 */
data class Capability(
    val type: String,
    val state: State? = null
)
