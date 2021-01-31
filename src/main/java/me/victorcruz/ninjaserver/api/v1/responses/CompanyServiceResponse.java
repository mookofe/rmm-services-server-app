package me.victorcruz.ninjaserver.api.v1.responses;

import lombok.Data;
import lombok.Builder;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;

@Data
@Builder
public class CompanyServiceResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long numberOfDevices;

    private static CompanyServiceResponse fromAggregate(CompanyServiceCount aggregate) {
        Service service = aggregate.getService();

        return CompanyServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .numberOfDevices(aggregate.getTotalDevices())
                .build();
    }

    public static List<CompanyServiceResponse> listFromAggregate(List<CompanyServiceCount> aggregates) {
        return aggregates.stream()
                .map(CompanyServiceResponse::fromAggregate)
                .collect(Collectors.toList());
    }
}
