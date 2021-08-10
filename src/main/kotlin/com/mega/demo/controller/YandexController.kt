package com.mega.demo.controller

import com.mega.demo.controller.model.yandex.Payload
import com.mega.demo.controller.model.yandex.YandexChangeStateRequest
import com.mega.demo.controller.model.yandex.YandexRequest
import com.mega.demo.controller.model.yandex.YandexResponse
import com.mega.demo.service.api.YandexService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

/**
 * Yandex controller.
 *
 * @author Alex Mikhalochkin
 */
@RestController
@RequestMapping("/v1.0")
class YandexController(val service: YandexService) {

    @RequestMapping(method = [RequestMethod.HEAD])
    fun health() {
        logger.info { "Yandex health check received" }
    }

    @PostMapping(path = ["/user/unlink"])
    fun unlink(@RequestHeader("X-Request-Id") requestId: String): Map<String, String> {
        logger.info { "Yandex account unlink. RequestId=$requestId" }
        return mapOf("request_id" to requestId)
    }

    @GetMapping(path = ["/user/devices"])
    fun getDevices(@RequestHeader("X-Request-Id") requestId: String): YandexResponse {
        logger.info { "Yandex. Get devices. Started. RequestId=$requestId" }
        val payload = Payload(service.getAllDevices(), "user-id")
        logger.info { "Yandex. Get devices. Finished. RequestId=$requestId" }
        return YandexResponse(requestId, payload)
    }

    @PostMapping(path = ["/user/devices/query"])
    fun refreshDevices(
        @RequestHeader("X-Request-Id") requestId: String,
        @RequestBody request: YandexRequest
    ): YandexResponse {
        val deviceIds = request.devices!!.map { it.id }
        logger.info { "Yandex. Refresh devices. Started. RequestId=$requestId, DeviceIds=$deviceIds" }
        val payload = Payload(service.getDeviceStates(deviceIds))
        logger.info { "Yandex. Refresh devices. Finished. RequestId=$requestId, DeviceIds=$deviceIds" }
        return YandexResponse(requestId, payload)
    }

    @PostMapping(path = ["/user/devices/action"])
    fun changeDevicesStates(
        @RequestHeader("X-Request-Id") requestId: String,
        @RequestBody request: YandexChangeStateRequest
    ): YandexResponse {
        val devices = request.payload.devices
        val deviceIds = devices.map { it.id }
        logger.info { "Yandex. Change devices states. Started. RequestId=$requestId, DeviceIds=$deviceIds" }
        val payload = Payload(service.changeState(devices))
        logger.info { "Yandex. Change devices states. Finished. RequestId=$requestId, DeviceIds=$deviceIds" }
        return YandexResponse(requestId, payload)
    }
}
