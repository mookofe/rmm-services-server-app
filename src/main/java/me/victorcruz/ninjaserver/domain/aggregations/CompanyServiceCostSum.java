package me.victorcruz.ninjaserver.domain.aggregations;

import me.victorcruz.ninjaserver.domain.models.Service;

public interface CompanyServiceCostSum {
    Service getService();
    Long getTotalCost();
}
