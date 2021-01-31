package me.victorcruz.ninjaserver.domain.services;

import java.util.List;
import org.springframework.stereotype.Service;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;

@Service
public class CompanyServiceFetcher {
    private DeviceServiceRepository deviceServiceRepository;

    public CompanyServiceFetcher(DeviceServiceRepository deviceServiceRepository) {
        this.deviceServiceRepository = deviceServiceRepository;
    }

    public List<CompanyServiceCount> fetch(String companyId) {
        return deviceServiceRepository.getCompanyServicesById(companyId);
    }
}
