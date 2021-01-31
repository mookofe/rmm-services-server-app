package me.victorcruz.ninjaserver.domain.exceptions;

public class DeviceServiceNotFoundException extends RuntimeException {
    public DeviceServiceNotFoundException() {
        super("Service for device was not found");
    }
}
