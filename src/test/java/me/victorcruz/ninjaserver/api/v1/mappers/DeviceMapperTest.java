package me.victorcruz.ninjaserver.api.v1.mappers;

import org.junit.jupiter.api.Test;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.types.DeviceType;
import me.victorcruz.ninjaserver.api.v1.requests.DeviceCreateRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeviceMapperTest {
    @Test
    void testItMapRequestToDevice() {
        // Given
        DeviceCreateRequest createRequest = DeviceCreateRequest.builder()
                .systemName("system-name")
                .type(DeviceType.WINDOWS_SERVER)
                .build();

        // When
        Device result = DeviceMapper.mapDeviceFromCreateApi(createRequest);

        // Then
        assertEquals("system-name", result.getSystemName());
        assertEquals(DeviceType.WINDOWS_SERVER, result.getType());
    }
}
