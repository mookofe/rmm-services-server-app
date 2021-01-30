package me.victorcruz.ninjaserver.domain.exceptions;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String companyId, String deviceId) {
        super(String.format("Device with id='%s' for company id='%s' was not found", deviceId, companyId));
    }
}
