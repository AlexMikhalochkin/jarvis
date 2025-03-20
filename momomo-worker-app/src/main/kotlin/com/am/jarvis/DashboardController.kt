package com.am.jarvis

import com.am.jarvis.core.api.DeviceProvider
import com.am.jarvis.core.api.DeviceStateChanger
import com.am.jarvis.core.model.DeviceState
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class DashboardController(
    val deviceProviders: List<DeviceProvider>,
    val deviceStateChanger: DeviceStateChanger
) {

    @GetMapping("/")
    fun getDashboard(model: Model): String {
        val bulbs = deviceProviders.flatMap { it.getAllDevices() }
            .map { Bulb(it.id, it.name.primaryName) }
        model.addAttribute("bulbs", bulbs)
        return "index"
    }

    @PostMapping("/bulbs/{id}/toggle")
    fun changeState(@PathVariable id: String, @RequestParam state: Boolean): ResponseEntity<String> {
        deviceStateChanger.changeStates(listOf(DeviceState(id, state)))
        return ResponseEntity.ok("OK")
    }

    data class Bulb(
        val id: String,
        val name: String
    )
}
