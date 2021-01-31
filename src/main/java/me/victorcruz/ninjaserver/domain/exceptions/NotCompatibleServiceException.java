package me.victorcruz.ninjaserver.domain.exceptions;

import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;

public class NotCompatibleServiceException extends RuntimeException {
    public NotCompatibleServiceException() {
        super("Device Operating System is not compatible with the device");
    }
}
