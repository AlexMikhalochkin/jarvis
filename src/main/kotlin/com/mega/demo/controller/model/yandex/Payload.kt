package com.mega.demo.controller.model.yandex

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents request payload.
 *
 * @author Alex Mikhalochkin
 */
data class Payload(
    val devices: List<Device>,
    @JsonProperty("user_id") val userId: String? = null
)
