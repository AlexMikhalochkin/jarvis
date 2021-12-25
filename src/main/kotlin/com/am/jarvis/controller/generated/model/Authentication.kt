package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

/**
 * Authentication information
 * @property tokenType
 * @property token
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class Authentication(
    @field:JsonProperty("tokenType") val tokenType: kotlin.String? = null,
    @field:JsonProperty("token") val token: kotlin.String? = null
)
