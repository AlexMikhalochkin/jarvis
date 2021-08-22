package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @property roomName
 * @property groups
 * @property categories
 */
data class DeviceContext(
    @field:JsonProperty("roomName") val roomName: kotlin.String? = null,
    @field:JsonProperty("groups") val groups: kotlin.collections.List<kotlin.String>? = null,
    @field:JsonProperty("categories") val categories: kotlin.collections.List<kotlin.String>? = null
)
