package me.victorcruz.ninjaserver.domain.exceptions;

public class ServiceAlreadyExistForDeviceException extends RuntimeException {
    public ServiceAlreadyExistForDeviceException(String serviceId, String deviceId) {
        super(String.format("Service with id='%s' already exist for device with id='%s'", serviceId, deviceId));
    }
}
