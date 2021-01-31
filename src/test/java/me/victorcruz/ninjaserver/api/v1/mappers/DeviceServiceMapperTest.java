package me.victorcruz.ninjaserver.api.v1.mappers;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.api.v1.responses.DeviceServiceResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeviceServiceMapperTest {
    @Test
    void testItMapModelToResponse() {
        // Given
        Device device = DeviceFactory.any();
        Service service = ServiceFactory.any();

        DeviceService model = DeviceService.builder()
                .id("4d5da7fc-420d-4b20-82ba-d87447f0182b")
                .device(device)
                .service(service)
                .createdAt(LocalDateTime.now())
                .build();

        // When
        DeviceServiceResponse result = DeviceServiceMapper.responseFromModel(model);

        // Then
        assertEquals(model.getId(), result.getId());
        assertEquals(service.getId(), result.getServiceId());
        assertEquals(service.getName(), result.getServiceName());
        assertEquals(service.getDescription(), result.getServiceDescription());
        assertEquals(model.getCreatedAt(), result.getPurchasedAt());
    }
}
