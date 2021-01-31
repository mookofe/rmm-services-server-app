package me.victorcruz.ninjaserver.domain.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import me.victorcruz.ninjaserver.domain.models.ServiceSummary;
import me.victorcruz.ninjaserver.domain.models.MonthlyCostSummary;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCostSum;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;

@Service
public class MonthlyCostCalculator {
    private static final Long BASE_DEVICE_COST = Long.valueOf("4");

    private DeviceServiceRepository deviceServiceRepository;
    private DeviceRepository deviceRepository;

    public MonthlyCostCalculator(DeviceServiceRepository deviceServiceRepository, DeviceRepository deviceRepository) {
        this.deviceServiceRepository = deviceServiceRepository;
        this.deviceRepository = deviceRepository;
    }

    public MonthlyCostSummary calculate(String companyId) {
        List<CompanyServiceCostSum> aggregates = deviceServiceRepository.getCompanyMonthlyCost(companyId);

        List<ServiceSummary> serviceSummaries = mapAggregateListToServiceSummary(aggregates);
        ServiceSummary baseCostSummary = buildDeviceCostSummary(companyId);
        serviceSummaries.add(baseCostSummary);

        Long totalCost = getTotalCost(serviceSummaries);

        return MonthlyCostSummary.builder()
                .totalCost(totalCost)
                .services(serviceSummaries)
                .build();
    }

    private Long getTotalCost(List<ServiceSummary> serviceSummaries) {
        return serviceSummaries.stream()
                .map(aggregate-> aggregate.getCost())
                .reduce(Long.valueOf("0"), Long::sum);
    }

    private List<ServiceSummary> mapAggregateListToServiceSummary(List<CompanyServiceCostSum> aggregates) {
        return aggregates.stream()
                .map(this::mapServiceAggregateToSummary)
                .collect(Collectors.toList());
    }

    private ServiceSummary mapServiceAggregateToSummary(CompanyServiceCostSum aggregate) {
        return ServiceSummary.builder()
                .serviceName(aggregate.getService().getName())
                .cost(aggregate.getTotalCost())
                .build();
    }

    private ServiceSummary buildDeviceCostSummary(String companyId) {
        int totalDevices = deviceRepository.findByCompanyId(companyId).size();

        return ServiceSummary.builder()
                .serviceName("Devices cost")
                .cost(totalDevices * BASE_DEVICE_COST)
                .build();
    }
}
