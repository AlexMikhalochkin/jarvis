package com.mega.demo.controller

import com.mega.demo.controller.model.yandex.ActionResult
import com.mega.demo.controller.model.yandex.Capability
import com.mega.demo.controller.model.yandex.Device
import com.mega.demo.controller.model.yandex.DeviceInfo
import com.mega.demo.controller.model.yandex.Payload
import com.mega.demo.controller.model.yandex.State
import com.mega.demo.controller.model.yandex.YandexChangeStateRequest
import com.mega.demo.controller.model.yandex.YandexRequest
import com.mega.demo.controller.model.yandex.YandexResponse
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
 * SmartThings controller.
 *
 * @author Alex Mikhalochkin
 */
@RestController
@RequestMapping("/v1.0")
class YandexController {

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
        val device = Device(
            "id",
            "свет на кухне",
            "цветная лампа",
            "спальня",
            "devices.types.light",
            mapOf("foo" to "bar"),
            listOf(Capability("devices.capabilities.on_off")),
            DeviceInfo("Provider2", "hue g11", "1.0", "1.0")
        )
        val payload = Payload(listOf(device), "user-id")
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
        val capability = Capability("devices.capabilities.on_off", State("on", true))
        val device = Device(
            "id",
            capabilities = listOf(capability),
        )
        val payload = Payload(listOf(device))
        logger.info { "Yandex. Refresh devices. Finished. RequestId=$requestId, DeviceIds=$deviceIds" }
        return YandexResponse(requestId, payload)
    }

    @PostMapping(path = ["/user/devices/action"])
    fun changeDevicesStates(
        @RequestHeader("X-Request-Id") requestId: String,
        @RequestBody request: YandexChangeStateRequest
    ): YandexResponse {
        val deviceIds = request.payload.devices.map { it.id }
        logger.info { "Yandex. Change devices states. Started. RequestId=$requestId, DeviceIds=$deviceIds" }
        val capability = Capability("devices.capabilities.on_off", State("on", true, ActionResult()))
        val device = Device(
            "id",
            capabilities = listOf(capability),
        )
        val payload = Payload(listOf(device))
        logger.info { "Yandex. Change devices states. Finished. RequestId=$requestId, DeviceIds=$deviceIds" }
        return YandexResponse(requestId, payload)
    }
}
