package com.am.jarvis

import com.am.jarvis.core.api.DeviceProvider
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DashboardController(
    val deviceProviders: List<DeviceProvider>
) {

    @GetMapping("/")
    fun getDashboard(model: Model): String {
        val bulbs = deviceProviders.flatMap { it.getAllDevices() }
            .map { Bulb(it.id, it.name.primaryName) }
        model.addAttribute("bulbs", bulbs)
        return "index"
    }

    data class Bulb(
        val id: String,
        val name: String
    )
}
