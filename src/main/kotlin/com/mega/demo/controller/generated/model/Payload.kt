package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Object with devices
 * @property userId
 * @property devices
 */
data class Payload(
    @field:JsonProperty("user_id", required = true) val userId: kotlin.String,
    @field:Valid
    @field:JsonProperty("devices", required = true)
    val devices: kotlin.collections.List<YandexDevice>
)
