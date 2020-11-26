package com.mikhalochkin.jarvis.service.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link SmartHomeService} for Google.
 *
 * @author Alex Mikhalochkin
 */
@Service
public class GoogleService implements SmartHomeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PlcClient plcClient;
    @Value("#{${jarvis.id.port.mapping}}")
    private Map<String, Integer> deviceToPortMapping;

    @Override
    public List<Device> getAllDevices() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return deviceToPortMapping.keySet().stream()
                .map(deviceId -> String.format("devices/%s.json", deviceId))
                .map(classLoader::getResourceAsStream)
                .map(this::readFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getStatuses(List<String> deviceIds) {
        Map<Integer, Boolean> outPortsStatuses = plcClient.getOutPortsStatuses();
        return deviceIds.stream()
                .map(id -> Pair.of(id, deviceToPortMapping.get(id)))
                .map(pair -> Pair.of(pair.getLeft(), outPortsStatuses.get(pair.getRight())))
                .map(pair -> Pair.of(pair.getLeft(), ImmutableMap.of("on", pair.getRight(), "online", true)))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    @Override
    public void changeStatus(List<String> deviceIds, Boolean isOn) {
        Consumer<Integer> idsConsumer = isOn
                ? plcClient::turnOn
                : plcClient::turnOff;
        deviceIds.stream()
                .map(deviceToPortMapping::get)
                .forEach(idsConsumer);
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
