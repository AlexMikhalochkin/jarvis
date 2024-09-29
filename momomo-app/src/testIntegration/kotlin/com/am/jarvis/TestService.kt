package com.am.jarvis

import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.Optional
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service

/**
 * Service for integration tests.
 *
 * @author Alex Mikhalochkin
 */
@Service
class TestService {

    /**
     * Returns content of the file.
     *
     * @param pathToFile path to the file with the payload.
     * @return content of the file.
     */
    fun getPayloadFromFile(pathToFile: String): String {
        return Optional.ofNullable(pathToFile)
            .map { this.javaClass.classLoader.getResourceAsStream(it) }
            .map { IOUtils.toString(it, StandardCharsets.UTF_8.name()) }
            .orElseThrow {
                FileNotFoundException(String.format(Locale.getDefault(), "File [%s] not found", pathToFile))
            }
    }
}
