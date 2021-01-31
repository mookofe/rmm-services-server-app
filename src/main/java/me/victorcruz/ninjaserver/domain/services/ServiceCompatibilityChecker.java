package me.victorcruz.ninjaserver.domain.services;

import org.springframework.stereotype.Component;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.types.DeviceType;
import me.victorcruz.ninjaserver.domain.exceptions.NotCompatibleServiceException;

@Component
public class ServiceCompatibilityChecker {
    public void checkOperatingSystem(Service service, Device device) {
        if (device.getType() == DeviceType.MAC) {
            validateMacService(service);
            return;
        }

        validateWindowsService(service);
    }

    private void validateMacService(Service service) {
        if (!service.getSupportsMac()) {
            throw new NotCompatibleServiceException();
        }
    }

    private void validateWindowsService(Service service) {
        if (!service.getSupportsWindows()) {
            throw new NotCompatibleServiceException();
        }
    }
}
