package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Request to get states of devices
 * @property devices
 */
data class StatesRequest(
    @field:Valid
    @field:JsonProperty("devices")
    val devices: kotlin.collections.List<ShortYandexDevice>? = null
)
