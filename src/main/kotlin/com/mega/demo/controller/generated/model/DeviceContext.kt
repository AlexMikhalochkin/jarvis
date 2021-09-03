package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

/**
 *
 * @property roomName
 * @property groups
 * @property categories
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class DeviceContext(
    @field:JsonProperty("roomName") val roomName: kotlin.String? = null,
    @field:JsonProperty("groups") val groups: kotlin.collections.List<kotlin.String>? = null,
    @field:JsonProperty("categories") val categories: kotlin.collections.List<kotlin.String>? = null
)
