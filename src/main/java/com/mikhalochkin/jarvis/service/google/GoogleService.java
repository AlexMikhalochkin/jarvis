package com.mikhalochkin.jarvis.service.google;

import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.model.google.status.LightStatus;
import com.mikhalochkin.jarvis.model.google.status.Status;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import com.mikhalochkin.jarvis.repository.api.DeviceRepository;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link SmartHomeService} for Google.
 *
 * @author Alex Mikhalochkin
 */
@Service
public class GoogleService implements SmartHomeService {

    @Autowired
    private PlcClient plcClient;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.getAll();
    }

    @Override
    public Map<String, ? extends Status> getStatuses(List<String> deviceIds) {
        Map<Integer, Boolean> outPortsStatuses = plcClient.getOutPortsStatuses();
        return deviceIds.stream()
                .map(deviceRepository::getById)
                .map(device -> Pair.of(device.getId(), outPortsStatuses.get(device.getPort())))
                .map(pair -> Pair.of(pair.getLeft(), new LightStatus(pair.getRight())))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    @Override
    public void changeStatus(List<String> deviceIds, Boolean isOn) {
        Consumer<Integer> idsConsumer = isOn
                ? plcClient::turnOn
                : plcClient::turnOff;
        deviceIds.stream()
                .map(deviceRepository::getById)
                .map(Device::getPort)
                .forEach(idsConsumer);
    }
}
