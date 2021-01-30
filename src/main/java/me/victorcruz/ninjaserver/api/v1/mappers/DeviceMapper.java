package me.victorcruz.ninjaserver.api.v1.mappers;

import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceCreateRequest;

public class DeviceMapper {
    public static Device mapDeviceFromCreateApi(DeviceCreateRequest request) {
        return Device.builder()
                .systemName(request.getSystemName())
                .type(request.getType())
                .build();
    }
}
