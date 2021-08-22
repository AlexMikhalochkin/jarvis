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
    @field:JsonProperty("schema", required = true) val schema: kotlin.String = "st-schema",
    @field:JsonProperty("version", required = true) val version: kotlin.String = "1.0",
    @field:JsonProperty("interactionType", required = true) val interactionType: kotlin.String,
    @field:JsonProperty("requestId", required = true) val requestId: kotlin.String
)
