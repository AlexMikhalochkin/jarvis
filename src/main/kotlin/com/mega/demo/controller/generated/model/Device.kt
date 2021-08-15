package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @property externalDeviceId
 * @property deviceCookie
 * @property friendlyName
 * @property manufacturerInfo
 * @property deviceContext
 * @property deviceHandlerType
 * @property deviceUniqueId
 */
data class Device(
    @field:JsonProperty("externalDeviceId") val externalDeviceId: kotlin.String? = null,
    @field:Valid @field:JsonProperty("deviceCookie") val deviceCookie: DeviceCookie? = null,
    @field:JsonProperty("friendlyName") val friendlyName: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("manufacturerInfo")
    val manufacturerInfo: ManufacturerInfo? = null,
    @field:Valid @field:JsonProperty("deviceContext") val deviceContext: DeviceContext? = null,
    @field:JsonProperty("deviceHandlerType") val deviceHandlerType: kotlin.String? = null,
    @field:JsonProperty("deviceUniqueId") val deviceUniqueId: kotlin.String? = null
)
