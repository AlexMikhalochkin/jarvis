package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Device's state
 * @property instance
 * @property actionResult
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStatesResponseState(
    @field:JsonProperty("instance") val instance: kotlin.String? = null,
    @field:Valid @field:JsonProperty("action_result") val actionResult: ActionResult? = null
)
