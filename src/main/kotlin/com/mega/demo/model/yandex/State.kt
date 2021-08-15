package com.mega.demo.model.yandex

/**
 * Represents state of the device.
 *
 * @author Alex Mikhalochkin
 */
data class State(
    val instance: String,
    val value: Any,
    val actionResult: ActionResult? = null
)
