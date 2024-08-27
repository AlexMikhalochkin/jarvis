package com.am.jarvis.controller

import com.am.jarvis.controller.generated.api.YandexApiDelegate
import com.am.jarvis.controller.generated.model.ChangeStatesRequest
import com.am.jarvis.controller.generated.model.ChangeStatesResponse
import com.am.jarvis.controller.generated.model.ChangeStatesResponseDevice
import com.am.jarvis.controller.generated.model.ChangeStatesResponsePayload
import com.am.jarvis.controller.generated.model.DevicesResponse
import com.am.jarvis.controller.generated.model.DevicesResponse2
import com.am.jarvis.controller.generated.model.Payload
import com.am.jarvis.controller.generated.model.Payload2
import com.am.jarvis.controller.generated.model.StatesRequest
import com.am.jarvis.controller.generated.model.UnlinkResponse
import com.am.jarvis.controller.generated.model.YandexDevice
import com.am.jarvis.controller.generated.model.YandexDeviceWithCapabilities
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import com.am.jarvis.service.api.SmartHomeService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of generated [YandexApiDelegate].
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexApiDelegateImpl(val smartHomeService: SmartHomeService, val conversionService: ConversionService) :
    YandexApiDelegate {

    @Value("\${yandex.user.id}")
    private lateinit var userId: String

    override fun changeDevicesStates(
        authorization: String,
        xRequestId: String,
        changeStatesRequest: ChangeStatesRequest
    ): ResponseEntity<ChangeStatesResponse> {
        val changeStateDevices = changeStatesRequest.payload.devices
        val deviceIds = changeStateDevices.map { it.id }
        logger.info { "Yandex. Change states. Started. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        val deviceStates = changeStateDevices.map { convert(it, DeviceState::class.java) }
        val changeStatesResponseDevices = smartHomeService.changeStates(deviceStates, Provider.YANDEX)
            .map { convert(it, ChangeStatesResponseDevice::class.java) }
        val payload = ChangeStatesResponsePayload(changeStatesResponseDevices)
        val changeStatesResponse = ChangeStatesResponse(xRequestId, payload)
        logger.info { "Yandex. Change states. Finished. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        return ResponseEntity.ok(changeStatesResponse)
    }

    override fun getDevices(authorization: String, xRequestId: String): ResponseEntity<DevicesResponse> {
        logger.info { "Yandex. Get devices. Started. RequestId=$xRequestId" }
        val devices = smartHomeService.getAllDevices()
            .map { convert(it, YandexDevice::class.java) }
        val payload = Payload(userId, devices)
        val devicesResponse = DevicesResponse(payload, xRequestId)
        logger.info { "Yandex. Get devices. Finished. RequestId=$xRequestId" }
        return ResponseEntity.ok(devicesResponse)
    }

    override fun getDevicesStates(
        authorization: String,
        xRequestId: String,
        statesRequest: StatesRequest?
    ): ResponseEntity<DevicesResponse2> {
        val deviceIds = statesRequest!!.devices!!.mapNotNull { it.id }
        logger.info { "Yandex. Get states. Started. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        val devices = smartHomeService.getDeviceStates(deviceIds)
            .map { convert(it, YandexDeviceWithCapabilities::class.java) }
        val payload = Payload2(devices)
        val devicesResponse2 = DevicesResponse2(xRequestId, payload)
        logger.info { "Yandex. Get states. Finished. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        return ResponseEntity.ok(devicesResponse2)
    }

    override fun health(): ResponseEntity<Unit> {
        logger.info { "Yandex. Health check" }
        return ResponseEntity.ok(Unit)
    }

    override fun unlink(authorization: String, xRequestId: String): ResponseEntity<UnlinkResponse> {
        logger.info { "Yandex. Account unlink. Started. RequestId=$xRequestId" }
        val unlinkResponse = UnlinkResponse(xRequestId)
        logger.info { "Yandex. Account unlink. Finished. RequestId=$xRequestId" }
        return ResponseEntity.ok(unlinkResponse)
    }

    private fun <T> convert(objectToConvert: Any, expectedType: Class<T>) =
        conversionService.convert(objectToConvert, expectedType)!!
}
