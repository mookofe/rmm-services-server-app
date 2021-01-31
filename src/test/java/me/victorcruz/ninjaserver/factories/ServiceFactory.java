package me.victorcruz.ninjaserver.factories;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import me.victorcruz.ninjaserver.domain.models.Service;

public class ServiceFactory {
    public static Service.ServiceBuilder builder() {
        LocalDateTime createdUpdatedAt = LocalDateTime.of(2021, 1, 30, 0, 0);

        return Service.builder()
                .id(UUID.randomUUID().toString())
                .name("Mac Antivirus")
                .price(new BigDecimal("7.00"))
                .description("service description")
                .supportsWindows(false)
                .supportsMac(true)
                .createdAt(createdUpdatedAt)
                .updatedAt(createdUpdatedAt);
    }

    public static Service any() {
        return builder()
                .build();
    }
}
