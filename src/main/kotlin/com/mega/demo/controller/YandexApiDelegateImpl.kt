package com.mega.demo.controller

import com.mega.demo.controller.generated.api.YandexApiDelegate
import com.mega.demo.controller.generated.model.ChangeStatesRequest
import com.mega.demo.controller.generated.model.ChangeStatesResponse
import com.mega.demo.controller.generated.model.ChangeStatesResponseDevice
import com.mega.demo.controller.generated.model.ChangeStatesResponsePayload
import com.mega.demo.controller.generated.model.DevicesResponse
import com.mega.demo.controller.generated.model.DevicesResponse2
import com.mega.demo.controller.generated.model.Payload
import com.mega.demo.controller.generated.model.Payload2
import com.mega.demo.controller.generated.model.StatesRequest
import com.mega.demo.controller.generated.model.UnlinkResponse
import com.mega.demo.controller.generated.model.YandexDevice
import com.mega.demo.controller.generated.model.YandexDeviceWithCapabilities
import com.mega.demo.model.yandex.Device
import com.mega.demo.service.api.YandexService
import mu.KotlinLogging
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
class YandexApiDelegateImpl(val service: YandexService, val conversionService: ConversionService) : YandexApiDelegate {

    override fun changeDevicesStates(
        authorization: String,
        xRequestId: String,
        changeStatesRequest: ChangeStatesRequest
    ): ResponseEntity<ChangeStatesResponse> {
        val devices = changeStatesRequest.payload.devices
        val deviceIds = devices.map { it.id }
        logger.info { "Yandex. Change states. Started. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        val mapNotNull1 = devices.mapNotNull { conversionService.convert(it, Device::class.java) }
        val mapNotNull = service.changeState(mapNotNull1)
            .mapNotNull { conversionService.convert(it, ChangeStatesResponseDevice::class.java) }
        val payload = ChangeStatesResponsePayload(mapNotNull)
        val changeStatesResponse = ChangeStatesResponse(xRequestId, payload)
        logger.info { "Yandex. Change states. Finished. RequestId=$xRequestId, DeviceIds=$deviceIds" }
        return ResponseEntity.ok(changeStatesResponse)
    }

    override fun getDevices(authorization: String, xRequestId: String): ResponseEntity<DevicesResponse> {
        logger.info { "Yandex. Get devices. Started. RequestId=$xRequestId" }
        val devices = service.getAllDevices()
            .mapNotNull { conversionService.convert(it, YandexDevice::class.java) }
        val payload = Payload("user-id", devices)
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
        val devices = service.getDeviceStates(deviceIds)
            .mapNotNull { conversionService.convert(it, YandexDeviceWithCapabilities::class.java) }
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
}
