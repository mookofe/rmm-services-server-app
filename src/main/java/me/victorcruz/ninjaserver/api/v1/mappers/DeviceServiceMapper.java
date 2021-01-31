package me.victorcruz.ninjaserver.api.v1.mappers;

import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.api.v1.responses.DeviceServiceResponse;

public class DeviceServiceMapper {
    public static DeviceServiceResponse responseFromModel(DeviceService deviceService) {
        Service service = deviceService.getService();

        return DeviceServiceResponse.builder()
                .id(deviceService.getId())
                .serviceId(service.getId())
                .serviceName(service.getName())
                .serviceDescription(service.getDescription())
                .purchasedAt(deviceService.getCreatedAt())
                .build();
    }
}
