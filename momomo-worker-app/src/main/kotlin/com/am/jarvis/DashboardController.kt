package com.am.jarvis

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DashboardController {

    @GetMapping("/")
    fun getDashboard(model: Model): String {
        val bulbs = listOf(
            Bulb(
                1,
                "Bulb 1"
            )
        )
        model.addAttribute("bulbs", bulbs)
        return "index"
    }

    data class Bulb(
        val id: Int,
        val name: String
    )
}
