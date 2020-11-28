package com.mikhalochkin.jarvis;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Service for integration tests.
 *
 * @author Alex Mikhalochkin
 */
@Service
public class TestService {

    /**
     * Returns content of the file.
     *
     * @param pathToFile path to the file with the payload.
     * @return content of the file.
     */
    public String getPayloadFromFile(String pathToFile) {
        return Optional.ofNullable(pathToFile)
                .map(this.getClass().getClassLoader()::getResourceAsStream)
                .map(this::getContent)
                .orElseThrow(() -> new RuntimeException(String.format("File [%s] not found", pathToFile)));
    }

    private String getContent(InputStream stream) {
        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
