package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @property schema
 * @property version
 * @property interactionType
 * @property requestId
 */
data class Headers(
    @field:JsonProperty("schema") val schema: kotlin.String? = null,
    @field:JsonProperty("version") val version: kotlin.String? = null,
    @field:JsonProperty("interactionType") val interactionType: kotlin.String? = null,
    @field:JsonProperty("requestId") val requestId: kotlin.String? = null
)
