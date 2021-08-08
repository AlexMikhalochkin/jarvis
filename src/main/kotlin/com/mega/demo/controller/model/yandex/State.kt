package com.mega.demo.controller.model.yandex

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents state of the device.
 *
 * @author Alex Mikhalochkin
 */
data class State(
    val instance: String,
    val value: Any,
    @JsonProperty("action_result") val actionResult: ActionResult? = null
)
