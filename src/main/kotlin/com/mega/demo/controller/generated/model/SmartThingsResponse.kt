package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @property headers
 * @property requestGrantCallbackAccess
 * @property devices
 */
data class SmartThingsResponse(
    @field:Valid @field:JsonProperty("headers") val headers: Headers? = null,
    @field:JsonProperty("requestGrantCallbackAccess")
    val requestGrantCallbackAccess: kotlin.Boolean? = null,
    @field:Valid @field:JsonProperty("devices") val devices: kotlin.collections.List<Device>? = null
)