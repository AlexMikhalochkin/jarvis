package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @property manufacturerName
 * @property modelName
 * @property hwVersion
 * @property swVersion
 */
data class ManufacturerInfo(
    @field:JsonProperty("manufacturerName") val manufacturerName: kotlin.String? = null,
    @field:JsonProperty("modelName") val modelName: kotlin.String? = null,
    @field:JsonProperty("hwVersion") val hwVersion: kotlin.String? = null,
    @field:JsonProperty("swVersion") val swVersion: kotlin.String? = null
)
