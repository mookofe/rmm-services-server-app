package me.victorcruz.ninjaserver.doubles;

import lombok.NoArgsConstructor;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCostSum;

@NoArgsConstructor
public class CompanyServiceCostSumStub implements CompanyServiceCostSum {
    private Long totalCost;
    private Service service;

    public CompanyServiceCostSumStub(Long totalCost, Service service) {
        this.totalCost = totalCost;
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public Long getTotalCost() {
        return totalCost;
    }
}
