package me.victorcruz.ninjaserver.domain.aggregations;

import me.victorcruz.ninjaserver.domain.models.Service;

public interface CompanyServiceCount {
    Service getService();
    Long getTotalDevices();
}
