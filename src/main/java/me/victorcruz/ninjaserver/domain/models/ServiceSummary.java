package me.victorcruz.ninjaserver.domain.models;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ServiceSummary {
    private String serviceName;
    private Long cost;
}
