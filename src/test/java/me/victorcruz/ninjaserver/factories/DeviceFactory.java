package me.victorcruz.ninjaserver.factories;

import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.time.LocalDateTime;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.types.DeviceType;

public class DeviceFactory {
    public static Device.DeviceBuilder builder() {
        LocalDateTime createdUpdatedAt = LocalDateTime.of(2021, 1, 30, 0, 0);

        return Device.builder()
                .id(UUID.randomUUID().toString())
                .companyId(UUID.randomUUID().toString())
                .systemName("system-name")
                .type(DeviceType.MAC)
                .createdAt(createdUpdatedAt)
                .updatedAt(createdUpdatedAt);
    }

    public static Device any() {
        return builder().build();
    }

    public static List<Device> list() {
        return Arrays.asList(
                any(),
                any()
        );
    }
}
