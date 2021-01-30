package me.victorcruz.ninjaserver.domain.services;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import me.victorcruz.ninjaserver.domain.exceptions.DeviceNotFoundException;

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

    public Device find(String companyId, String deviceId) {
        Device device = deviceRepository.findByCompanyIdAndId(companyId, deviceId);

        if (null == device) {
            throw new DeviceNotFoundException(companyId, deviceId);
        }

        return device;
    }

    public Device update(Device device) {
        device.setUpdatedAt(LocalDateTime.now());

        return deviceRepository.save(device);
    }

    public void delete(String companyId, String deviceId) {
        Device device = this.find(companyId, deviceId);

        deviceRepository.delete(device);
    }
}
