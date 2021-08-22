package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * User's device with its capabilities
 * @property id
 * @property capabilities
 */
data class YandexDeviceWithCapabilities(
    @field:JsonProperty("id") val id: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("capabilities")
    val capabilities: kotlin.collections.List<FullCapability>? = null
)
