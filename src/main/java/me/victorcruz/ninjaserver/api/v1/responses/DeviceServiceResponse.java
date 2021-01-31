package me.victorcruz.ninjaserver.api.v1.responses;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
@Data
public class DeviceServiceResponse {
    private String id;
    private String serviceId;
    private String serviceName;
    private String serviceDescription;
    private LocalDateTime purchasedAt;
}
