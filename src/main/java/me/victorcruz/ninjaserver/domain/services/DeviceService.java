package me.victorcruz.ninjaserver.domain.services;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device store(String companyId, Device device) {
        device.setCompanyId(companyId);
        device.setCreatedAt(LocalDateTime.now());
        device.setUpdatedAt(LocalDateTime.now());

        return deviceRepository.save(device);
    }

    public List<Device> getDevices(String companyId) {
        return deviceRepository.findByCompanyId(companyId);
    }
}
