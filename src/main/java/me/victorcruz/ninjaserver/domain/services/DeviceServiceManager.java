package me.victorcruz.ninjaserver.domain.services;

import java.time.LocalDateTime;
import javax.validation.ValidationException;
import org.springframework.stereotype.Component;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.domain.repositories.ServiceRepository;
import me.victorcruz.ninjaserver.domain.exceptions.DeviceNotFoundException;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;
import me.victorcruz.ninjaserver.domain.exceptions.ServiceAlreadyExistForDeviceException;

@Component
public class DeviceServiceManager {
    private final DeviceManager deviceManager;
    private final ServiceRepository serviceRepository;
    private final DeviceServiceRepository deviceServiceRepository;
    private final ServiceCompatibilityChecker serviceCompatibilityChecker;

    public DeviceServiceManager(
            DeviceManager deviceManager,
            ServiceRepository serviceRepository,
            DeviceServiceRepository deviceServiceRepository,
            ServiceCompatibilityChecker serviceCompatibilityChecker
    ) {
        this.deviceManager = deviceManager;
        this.serviceRepository = serviceRepository;
        this.deviceServiceRepository = deviceServiceRepository;
        this.serviceCompatibilityChecker = serviceCompatibilityChecker;
    }

    public DeviceService addService(String companyId, String deviceId, String serviceId) {
        Device device = getDevice(companyId, deviceId);
        Service service = getService(serviceId);
        checkIfDeviceAlreadyHasService(device, service);
        serviceCompatibilityChecker.checkOperatingSystem(service, device);

        DeviceService deviceService = DeviceService.builder()
                .device(device)
                .service(service)
                .createdAt(LocalDateTime.now())
                .build();

        return deviceServiceRepository.save(deviceService);
    }

    private Device getDevice(String companyId, String deviceId) {
        try {
            return deviceManager.find(companyId, deviceId);
        } catch (DeviceNotFoundException ex) {
            throw new ValidationException("Not able to add service to device. Device was not found", ex);
        }
    }

    private Service getService(String serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ValidationException("Not able to add service to device. Service was not found"));
    }

    private void checkIfDeviceAlreadyHasService(Device device, Service service) {
        DeviceService deviceService = deviceServiceRepository.findByDeviceIdAndServiceId(device.getId(), service.getId());

        if (null != deviceService) {
            throw new ServiceAlreadyExistForDeviceException(service.getId(), device.getId());
        }
    }
}
