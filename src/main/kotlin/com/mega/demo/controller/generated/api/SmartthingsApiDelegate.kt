package com.mega.demo.controller.generated.api

import com.mega.demo.controller.generated.model.SmartThingsRequest
import com.mega.demo.controller.generated.model.SmartThingsResponse
import javax.annotation.Generated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * A delegate to be called by the [SmartthingsApiController]. Implement this interface with a
 * [org.springframework.stereotype.Component] annotated class.
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
interface SmartthingsApiDelegate {

    /** @see SmartthingsApi#handleSmartThings */
    fun handleSmartThings(
        smartThingsRequest: SmartThingsRequest
    ): ResponseEntity<SmartThingsResponse> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
