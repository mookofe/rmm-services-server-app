package me.victorcruz.ninjaserver.factories;

import java.util.UUID;
import java.time.LocalDateTime;
import me.victorcruz.ninjaserver.domain.models.DeviceService;

public class DeviceServiceFactory {
    public static DeviceService.DeviceServiceBuilder builder() {
        LocalDateTime createdAt = LocalDateTime.of(2021, 1, 30, 0, 0);

        return DeviceService.builder()
                .id(UUID.randomUUID().toString())
                .device(DeviceFactory.any())
                .service(ServiceFactory.any())
                .createdAt(createdAt);
    }

    public static DeviceService any() {
        return builder().build();
    }
}
