package com.am.jarvis

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * Verification for [JarvisApplication].
 *
 * @author Alex Mikhalochkin
 */
class JarvisApplicationTest {

    private val jarvisApplication = JarvisApplication()

    @Test
    fun testWebClient() {
        assertNotNull(jarvisApplication.webClient())
    }

    @Test
    fun testRetryBackoffSpec() {
        assertNotNull(jarvisApplication.retryBackoffSpec(1, 200L))
    }
}
