package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * SmartThings device state
 * @property externalDeviceId
 * @property deviceCookie
 * @property states
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class DeviceState(
    @field:JsonProperty("externalDeviceId") val externalDeviceId: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("deviceCookie")
    val deviceCookie: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null,
    @field:Valid @field:JsonProperty("states") val states: kotlin.collections.List<State>? = null
)
