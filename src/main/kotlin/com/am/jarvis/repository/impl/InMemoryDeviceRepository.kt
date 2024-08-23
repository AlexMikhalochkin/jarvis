package com.am.jarvis.repository.impl

import com.am.jarvis.model.Device
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import com.am.jarvis.model.TechnicalInfo
import com.am.jarvis.repository.api.DeviceRepository
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
class InMemoryDeviceRepository : DeviceRepository {

    private lateinit var devices: List<Device>
    private lateinit var idsToPorts: Map<String, Int>
    private val port = 23
    private val storedStates = mutableMapOf(
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

    override fun findStates(deviceIds: List<String>): List<DeviceState> {
        return deviceIds.map { it to storedStates.getValue(idsToPorts.getValue(it)) }
                .map { (deviceId, status) -> DeviceState(deviceId, null, status) }
                .toList()
    }

    override fun updateStates(states: List<DeviceState>) {
        val newStates = states.associate { (it.port ?: idsToPorts[it.deviceId])!! to it.isOn!! }
        storedStates.putAll(newStates)
    }

    @Suppress("LongMethod", "MagicNumber")
    @PostConstruct
    fun init() {
        devices = listOf(
                Device(
                        "child-light-0",
                        13,
                        mapOf(Provider.YANDEX to "Детская", Provider.SMART_THINGS to "Child's Room"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в детской", Provider.SMART_THINGS to "Toilet Bulb"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "bath-light-0",
                        12,
                        mapOf(Provider.YANDEX to "Ванная", Provider.SMART_THINGS to "Bathroom"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в ванной", Provider.SMART_THINGS to "Bathroom light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "cor-light-0",
                        10,
                        mapOf(Provider.YANDEX to "Корридор", Provider.SMART_THINGS to "Corridor"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в корридоре", Provider.SMART_THINGS to "Corridor light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "prih-light-0",
                        9,
                        mapOf(Provider.YANDEX to "Прихожая", Provider.SMART_THINGS to "Hallway"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в прихожей", Provider.SMART_THINGS to "Hallway light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "toilet-light-0",
                        7,
                        mapOf(Provider.YANDEX to "Туалет", Provider.SMART_THINGS to "Toilet"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в туалете", Provider.SMART_THINGS to "Toilet light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "kitchen-light-0",
                        port,
                        mapOf(Provider.YANDEX to "Кухня", Provider.SMART_THINGS to "Kitchen"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет на кухне", Provider.SMART_THINGS to "Kitchen Blightulb"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("Kitchen Lights", "House Lights")
                ),
                Device(
                        "living-light-0",
                        27,
                        mapOf(Provider.YANDEX to "Гостиная", Provider.SMART_THINGS to "Living Room"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет над диваном", Provider.SMART_THINGS to "Couch light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("Living Room Lights", "House Lights")
                ),
                Device(
                        "living-light-1",
                        25,
                        mapOf(Provider.YANDEX to "Гостиная", Provider.SMART_THINGS to "Living Room"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет над игрушками", Provider.SMART_THINGS to "Toys light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("Living Room Lights", "House Lights")
                ),
                Device(
                        "bed-light-1",
                        11,
                        mapOf(Provider.YANDEX to "Спальня", Provider.SMART_THINGS to "Bedroom"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Свет в спальне", Provider.SMART_THINGS to "Bedroom light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("House Lights")
                ),
                Device(
                        "kitchen-light-1",
                        24,
                        mapOf(Provider.YANDEX to "Кухня", Provider.SMART_THINGS to "Kitchen"),
                        mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
                        mapOf(Provider.YANDEX to "Подсветка кухни", Provider.SMART_THINGS to "Kitchen light"),
                        TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
                        "цветная лампа",
                        listOf("devices.capabilities.on_off"),
                        listOf("light", "switch"),
                        listOf("Kitchen Lights", "House Lights")
                ),
        )
        idsToPorts = devices.associate { it.id to it.port }
    }
}
