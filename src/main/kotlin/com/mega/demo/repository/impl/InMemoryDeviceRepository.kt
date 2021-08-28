package com.mega.demo.repository.impl

import com.mega.demo.model.Device
import com.mega.demo.model.Provider
import com.mega.demo.model.TechnicalInfo
import com.mega.demo.repository.api.DeviceRepository
import javax.annotation.PostConstruct
import org.springframework.stereotype.Repository

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
class InMemoryDeviceRepository : DeviceRepository {

    private lateinit var devices: List<Device>
    private lateinit var idsToPorts: Map<String, Int>
    private val port = 7
    private val outPorts = mutableMapOf(
        7 to false,
        8 to false,
        9 to false,
        10 to false,
        11 to false,
        12 to false,
        13 to false,
        22 to false,
        23 to false,
        24 to false,
        25 to false,
        26 to false,
        27 to false,
        28 to false
    )

    override fun findAll(): List<Device> {
        return devices
    }

    override fun findPorts(deviceIds: List<String>): Map<String, Int> {
        return deviceIds.associateWith { idsToPorts.getValue(it) }
    }

    override fun getStatuses(values: Collection<Int>): Map<Int, Boolean> {
        return outPorts
    }

    @PostConstruct
    fun init() {
        val device = Device(
            "kitchen-light-0",
            port,
            mapOf(Provider.YANDEX to "Кухня", Provider.SMART_THINGS to "Kitchen"),
            mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
            mapOf(Provider.YANDEX to "свет на кухне", Provider.SMART_THINGS to "Kitchen Bulb"),
            TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
            "цветная лампа",
            listOf("devices.capabilities.on_off"),
            listOf("light", "switch"),
            listOf("Kitchen Lights", "House Bulbs")
        )
        devices = listOf(device)
        idsToPorts = devices.associate { it.id to it.port }
    }
}
