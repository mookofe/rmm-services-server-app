package me.victorcruz.ninjaserver.api.v1.requests;

import lombok.Data;
import lombok.Builder;
import javax.validation.constraints.NotNull;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.types.DeviceType;

@Data
@Builder
public class DeviceCreateRequest {
    @NotNull
    private String systemName;

    @NotNull
    private DeviceType type;

    public Device mapToDevice() {
        return Device.builder()
                .systemName(systemName)
                .type(type)
                .build();
    }
}
