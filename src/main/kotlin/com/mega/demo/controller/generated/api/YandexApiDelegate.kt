package com.mega.demo.controller.generated.api

import com.mega.demo.controller.generated.model.ChangeStatesRequest
import com.mega.demo.controller.generated.model.ChangeStatesResponse
import com.mega.demo.controller.generated.model.DevicesResponse
import com.mega.demo.controller.generated.model.DevicesResponse2
import com.mega.demo.controller.generated.model.StatesRequest
import com.mega.demo.controller.generated.model.UnlinkResponse
import javax.annotation.Generated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * A delegate to be called by the [YandexApiController]. Implement this interface with a
 * [org.springframework.stereotype.Component] annotated class.
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
interface YandexApiDelegate {

    /** @see YandexApi#changeDevicesStates */
    fun changeDevicesStates(
        authorization: kotlin.String,
        xRequestId: kotlin.String,
        changeStatesRequest: ChangeStatesRequest
    ): ResponseEntity<ChangeStatesResponse> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    /** @see YandexApi#getDevices */
    fun getDevices(
        authorization: kotlin.String,
        xRequestId: kotlin.String
    ): ResponseEntity<DevicesResponse> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    /** @see YandexApi#getDevicesStates */
    fun getDevicesStates(
        authorization: kotlin.String,
        xRequestId: kotlin.String,
        statesRequest: StatesRequest?
    ): ResponseEntity<DevicesResponse2> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    /** @see YandexApi#health */
    fun health(): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    /** @see YandexApi#unlink */
    fun unlink(
        authorization: kotlin.String,
        xRequestId: kotlin.String
    ): ResponseEntity<UnlinkResponse> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
