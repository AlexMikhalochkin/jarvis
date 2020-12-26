package com.mikhalochkin.jarvis.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.repository.api.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FileSystemDeviceRepository implements DeviceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemDeviceRepository.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("#{${jarvis.id.port.mapping}}")
    private Map<String, Integer> deviceToPortMapping;
    private Map<String, Device> devices;

    @PostConstruct
    void init() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        devices = deviceToPortMapping.keySet().stream()
                .map(deviceId -> String.format("devices/%s.json", deviceId))
                .map(classLoader::getResourceAsStream)
                .map(this::readFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Device::getId, Function.identity()));
    }

    @Override
    public List<Device> getAll() {
        return new ArrayList<>(devices.values());
    }

    @Override
    public Device getById(String id) {
        return devices.get(id);
    }

    private Device readFile(InputStream inputStream) {
        try {
            return objectMapper.readValue(inputStream, Device.class);
        } catch (IOException e) {
            LOGGER.error("Cannot read file with device info.");
            return null;
        }
    }
}
