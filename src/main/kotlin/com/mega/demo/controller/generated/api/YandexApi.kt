/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech Do not edit the class manually.
 */
package com.mega.demo.controller.generated.api

import com.mega.demo.controller.generated.model.ChangeStatesRequest
import com.mega.demo.controller.generated.model.ChangeStatesResponse
import com.mega.demo.controller.generated.model.DevicesResponse
import com.mega.demo.controller.generated.model.DevicesResponse2
import com.mega.demo.controller.generated.model.StatesRequest
import com.mega.demo.controller.generated.model.UnlinkResponse
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Validated
interface YandexApi {

    fun getDelegate(): YandexApiDelegate = object : YandexApiDelegate {}

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/yandex/v1.0/user/devices/action"],
        produces = ["application/json"],
        consumes = ["application/json"])
    fun changeDevicesStates(
        @RequestHeader(value = "Authorization", required = true) authorization: kotlin.String,
        @RequestHeader(value = "X-Request-Id", required = true) xRequestId: kotlin.String,
        @Valid @RequestBody changeStatesRequest: ChangeStatesRequest
    ): ResponseEntity<ChangeStatesResponse> {
        return getDelegate().changeDevicesStates(authorization, xRequestId, changeStatesRequest)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/yandex/v1.0/user/devices"],
        produces = ["application/json"])
    fun getDevices(
        @RequestHeader(value = "Authorization", required = true) authorization: kotlin.String,
        @RequestHeader(value = "X-Request-Id", required = true) xRequestId: kotlin.String
    ): ResponseEntity<DevicesResponse> {
        return getDelegate().getDevices(authorization, xRequestId)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/yandex/v1.0/user/devices/query"],
        produces = ["application/json"],
        consumes = ["application/json"])
    fun getDevicesStates(
        @RequestHeader(value = "Authorization", required = true) authorization: kotlin.String,
        @RequestHeader(value = "X-Request-Id", required = true) xRequestId: kotlin.String,
        @Valid @RequestBody(required = false) statesRequest: StatesRequest?
    ): ResponseEntity<DevicesResponse2> {
        return getDelegate().getDevicesStates(authorization, xRequestId, statesRequest)
    }

    @RequestMapping(method = [RequestMethod.HEAD], value = ["/yandex/v1.0"])
    fun health(): ResponseEntity<Unit> {
        return getDelegate().health()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/yandex/v1.0/user/unlink"],
        produces = ["application/json"])
    fun unlink(
        @RequestHeader(value = "Authorization", required = true) authorization: kotlin.String,
        @RequestHeader(value = "X-Request-Id", required = true) xRequestId: kotlin.String
    ): ResponseEntity<UnlinkResponse> {
        return getDelegate().unlink(authorization, xRequestId)
    }
}