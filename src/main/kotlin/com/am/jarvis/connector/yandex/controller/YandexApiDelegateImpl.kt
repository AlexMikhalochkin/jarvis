package com.am.jarvis.connector.yandex.controller

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
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Implementation of generated [YandexApiDelegate].
 *
 * @author Alex Mikhalochkin
 */
@Component
class YandexApiDelegateImpl(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService,
    @Value("\${yandex.user-id}") private val userId: String
) : YandexApiDelegate {

    override fun changeDevicesStates(
        authorization: String,
        xRequestId: String,
        changeStatesRequest: ChangeStatesRequest
    ): ResponseEntity<ChangeStatesResponse> {
        val deviceStates = changeStatesRequest.payload
            .devices
            .map { convert(it, DeviceState::class.java) }
        val changeStatesResponseDevices = smartHomeService.changeStates(deviceStates, "YANDEX")
            .map { convert(it, ChangeStatesResponseDevice::class.java) }
        val payload = ChangeStatesResponsePayload(changeStatesResponseDevices)
        val changeStatesResponse = ChangeStatesResponse(xRequestId, payload)
        return ResponseEntity.ok(changeStatesResponse)
    }

    override fun getDevices(authorization: String, xRequestId: String): ResponseEntity<DevicesResponse> {
        val devices = smartHomeService.getAllDevices()
            .map { convert(it, YandexDevice::class.java) }
        val payload = Payload(userId, devices)
        val devicesResponse = DevicesResponse(payload, xRequestId)
        return ResponseEntity.ok(devicesResponse)
    }

    override fun getDevicesStates(
        authorization: String,
        xRequestId: String,
        statesRequest: StatesRequest?
    ): ResponseEntity<DevicesResponse2> {
        val deviceIds = statesRequest!!.devices!!.mapNotNull { it.id }
        val devices = smartHomeService.getDeviceStates(deviceIds)
            .map { convert(it, YandexDeviceWithCapabilities::class.java) }
        val payload = Payload2(devices)
        val devicesResponse2 = DevicesResponse2(xRequestId, payload)
        return ResponseEntity.ok(devicesResponse2)
    }

    override fun health(): ResponseEntity<Unit> {
        return ResponseEntity.ok(Unit)
    }

    override fun unlink(authorization: String, xRequestId: String): ResponseEntity<UnlinkResponse> {
        val unlinkResponse = UnlinkResponse(xRequestId)
        return ResponseEntity.ok(unlinkResponse)
    }

    private fun <T> convert(objectToConvert: Any, expectedType: Class<T>) =
        conversionService.convert(objectToConvert, expectedType)!!
}
