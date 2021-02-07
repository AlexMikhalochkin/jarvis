package com.mikhalochkin.jarvis.service.samsung;

import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * TODO: add description
 *
 * @author Alex Mikhalochkin
 */
@Service
@Qualifier("samsungService")
public class SamsungService implements SmartHomeService {

    @Autowired
    private PlcClient plcClient;
    @Value("#{${jarvis.id.port.mapping}}")
    private Map<String, Integer> deviceToPortMapping;

    @Override
    public List<Device> getAllDevices() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Map<String, Object> getStatuses(List<String> deviceIds) {
        Map<Integer, Boolean> outPortsStatuses = plcClient.getOutPortsStatuses();
        return deviceIds.stream()
                .map(id -> Pair.of(id, deviceToPortMapping.get(id)))
                .map(pair -> Pair.of(pair.getLeft(), outPortsStatuses.get(pair.getRight())))
                .map(pair -> Pair.of(pair.getLeft(), pair.getRight() ? "on" : "off"))
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
}
