package me.victorcruz.ninjaserver.api.v1.requests;

import org.junit.jupiter.api.Test;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.types.DeviceType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeviceCreateRequestTest {
    @Test
    void testItMapRequestToDevice() {
        // Given
        DeviceCreateRequest sut = DeviceCreateRequest.builder()
                .systemName("system-name")
                .type(DeviceType.WINDOWS_SERVER)
                .build();

        // When
        Device result = sut.mapToDevice();

        // Then
        assertEquals("system-name", result.getSystemName());
        assertEquals(DeviceType.WINDOWS_SERVER, result.getType());
    }
}
