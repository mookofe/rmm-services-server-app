package me.victorcruz.ninjaserver.domain.models;

import lombok.Getter;
import lombok.Builder;
import java.util.List;

@Builder
@Getter
public class MonthlyCostSummary {
    private Long totalCost;
    private List<ServiceSummary> services;
}
