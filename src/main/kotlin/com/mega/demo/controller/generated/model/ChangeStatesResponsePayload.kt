package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Object with devices
 * @property devices
 */
data class ChangeStatesResponsePayload(
    @field:Valid
    @field:JsonProperty("devices")
    val devices: kotlin.collections.List<ChangeStatesResponseDevice>? = null
)
