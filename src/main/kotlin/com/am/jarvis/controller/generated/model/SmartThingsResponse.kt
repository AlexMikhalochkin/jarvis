package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 *
 * @property headers
 * @property requestGrantCallbackAccess
 * @property devices
 * @property deviceState
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class SmartThingsResponse(
    @field:Valid @field:JsonProperty("headers") val headers: Headers? = null,
    @field:JsonProperty("requestGrantCallbackAccess")
    val requestGrantCallbackAccess: kotlin.Boolean? = null,
    @field:Valid
    @field:JsonProperty("devices")
    val devices: kotlin.collections.List<SmartThingsDevice>? = null,
    @field:Valid
    @field:JsonProperty("deviceState")
    val deviceState: kotlin.collections.List<DeviceState>? = null
)
