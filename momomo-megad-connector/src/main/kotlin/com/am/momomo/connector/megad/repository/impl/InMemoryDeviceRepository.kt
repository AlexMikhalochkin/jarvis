package com.am.momomo.connector.megad.repository.impl

import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceName
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Room
import org.springframework.stereotype.Repository

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
@Suppress("LongMethod", "MagicNumber")
internal class InMemoryDeviceRepository : DeviceRepository {

    private val devices: List<Device>
    private val idsToPorts: Map<String, Int>
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

    override fun findStates(deviceIds: List<String>): List<DeviceState> {
        return deviceIds.map { it to storedStates.getValue(idsToPorts.getValue(it)) }
            .map { (deviceId, status) -> DeviceState(deviceId, status) }
            .toList()
    }

    override fun updateStates(states: List<DeviceState>) {
        val newStates = states.associate { (it.customData["port"] as Int? ?: idsToPorts[it.deviceId])!! to it.isOn }
        storedStates.putAll(newStates)
    }

    override fun getDeviceByPort(port: Int): Device {
        return devices.find { it.additionalData["port"] == port }!!
    }

    override fun findPortByDeviceId(deviceId: String): Int {
        return idsToPorts.getValue(deviceId)
    }

    init {
        devices = listOf(
            Device(
                "child-light-0",
                Room("Child's Room", "Детская"),
                DeviceName("Toilet Bulb", "Свет в детской"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 13)
            ),
            Device(
                "bath-light-0",
                Room("Bathroom", "Ванная"),
                DeviceName("Bathroom light", "Свет в ванной"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 12)
            ),
            Device(
                "cor-light-0",
                Room("Corridor", "Корридор"),
                DeviceName("Corridor light", "Свет в корридоре"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 10)
            ),
            Device(
                "prih-light-0",
                Room("Hallway", "Прихожая"),
                DeviceName("Hallway light", "Свет в прихожей"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 9)
            ),
            Device(
                "toilet-light-0",
                Room("Toilet", "Туалет"),
                DeviceName("Toilet light", "Свет в туалете"),
                "цветн��я лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 7)
            ),
            Device(
                "kitchen-light-0",
                Room("Kitchen", "Кухня"),
                DeviceName("Kitchen Blightulb", "Свет на кухне"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("Kitchen Lights", "House Lights"),
                mapOf("port" to 23)
            ),
            Device(
                "living-light-0",
                Room("Living Room", "Гостиная"),
                DeviceName("Couch light", "Свет над диваном"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("Living Room Lights", "House Lights"),
                mapOf("port" to 27)
            ),
            Device(
                "living-light-1",
                Room("Living Room", "Гостиная"),
                DeviceName("Toys light", "Свет над и��рушками"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("Living Room Lights", "House Lights"),
                mapOf("port" to 25)
            ),
            Device(
                "bed-light-1",
                Room("Bedroom", "Спальня"),
                DeviceName("Bedroom light", "Свет в спальне"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("House Lights"),
                mapOf("port" to 11)
            ),
            Device(
                "kitchen-light-1",
                Room("Kitchen", "Кухня"),
                DeviceName("Kitchen light", "Подсветка кухни"),
                "цветная лампа",
                listOf("devices.capabilities.on_off"),
                listOf("light", "switch"),
                listOf("Kitchen Lights", "House Lights"),
                mapOf("port" to 24)
            )
        )
        idsToPorts = devices.associate { it.id to it.additionalData["port"] as Int }
    }
}
