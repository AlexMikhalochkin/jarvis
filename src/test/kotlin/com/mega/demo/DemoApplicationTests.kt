package com.mega.demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DemoApplicationTests {

	@Test
	fun testWebClient() {
		val demoApplication = DemoApplication()
		assertNotNull(demoApplication.webClient())
	}
}
