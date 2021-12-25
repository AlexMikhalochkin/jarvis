package com.am.jarvis.controller.generated.api

import com.am.jarvis.controller.generated.model.ChangeStatesRequest
import com.am.jarvis.controller.generated.model.ChangeStatesResponse
import com.am.jarvis.controller.generated.model.DevicesResponse
import com.am.jarvis.controller.generated.model.DevicesResponse2
import com.am.jarvis.controller.generated.model.StatesRequest
import com.am.jarvis.controller.generated.model.UnlinkResponse
import javax.annotation.Generated
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
    ): ResponseEntity<ChangeStatesResponse>

    /** @see YandexApi#getDevices */
    fun getDevices(
        authorization: kotlin.String,
        xRequestId: kotlin.String
    ): ResponseEntity<DevicesResponse>

    /** @see YandexApi#getDevicesStates */
    fun getDevicesStates(
        authorization: kotlin.String,
        xRequestId: kotlin.String,
        statesRequest: StatesRequest?
    ): ResponseEntity<DevicesResponse2>

    /** @see YandexApi#health */
    fun health(): ResponseEntity<Unit>

    /** @see YandexApi#unlink */
    fun unlink(
        authorization: kotlin.String,
        xRequestId: kotlin.String
    ): ResponseEntity<UnlinkResponse>
}
