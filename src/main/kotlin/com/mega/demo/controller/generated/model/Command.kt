package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * SmartThings command
 * @property component
 * @property capability
 * @property command
 * @property arguments
 */
data class Command(
    @field:JsonProperty("component") val component: kotlin.String? = "main",
    @field:JsonProperty("capability") val capability: kotlin.String? = null,
    @field:JsonProperty("command") val command: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("arguments")
    val arguments: kotlin.collections.List<kotlin.Any>? = null
)
