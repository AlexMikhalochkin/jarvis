package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Device's capability
 * @property id
 * @property capabilities
 */
data class ChangeStatesResponseDevice(
    @field:JsonProperty("id") val id: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("capabilities")
    val capabilities: kotlin.collections.List<ChangeStatesResponseCapabity>? = null
)
