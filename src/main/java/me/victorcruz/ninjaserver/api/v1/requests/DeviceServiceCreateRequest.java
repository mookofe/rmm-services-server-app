package me.victorcruz.ninjaserver.api.v1.requests;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceServiceCreateRequest {
    @NotNull
    private String serviceId;
}
