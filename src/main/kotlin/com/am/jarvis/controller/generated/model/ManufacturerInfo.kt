package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

/**
 *
 * @property manufacturerName
 * @property modelName
 * @property hwVersion
 * @property swVersion
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ManufacturerInfo(
    @field:JsonProperty("manufacturerName") val manufacturerName: kotlin.String? = null,
    @field:JsonProperty("modelName") val modelName: kotlin.String? = null,
    @field:JsonProperty("hwVersion") val hwVersion: kotlin.String? = null,
    @field:JsonProperty("swVersion") val swVersion: kotlin.String? = null
)
