package me.victorcruz.ninjaserver.domain.services;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import me.victorcruz.ninjaserver.domain.models.MonthlyCostSummary;
import me.victorcruz.ninjaserver.doubles.CompanyServiceCostSumStub;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCostSum;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;

import java.util.List;
import java.util.Arrays;
import java.math.BigDecimal;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MonthlyCostCalculatorTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";

    private MonthlyCostCalculator sut;

    @Mock
    private DeviceServiceRepository deviceServiceRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        sut = new MonthlyCostCalculator(deviceServiceRepository, deviceRepository);
    }

    @Test
    void testItCanCalculateMonthlyCostSuccessfully() {
        // Given
        List<CompanyServiceCostSum> aggregates = buildAggregateList();
        when(deviceServiceRepository.getCompanyMonthlyCost(COMPANY_ID)).thenReturn(aggregates);
        when(deviceRepository.findByCompanyId(COMPANY_ID)).thenReturn(DeviceFactory.list());

        // When
        MonthlyCostSummary result = sut.calculate(COMPANY_ID);

        // Then
        assertEquals(20, result.getTotalCost());
        assertEquals("Mac Antivirus", result.getServices().get(0).getServiceName());
        assertEquals(7, result.getServices().get(0).getCost());
        assertEquals("Cloudberry", result.getServices().get(1).getServiceName());
        assertEquals(3, result.getServices().get(1).getCost());
        assertEquals("PSA", result.getServices().get(2).getServiceName());
        assertEquals(2, result.getServices().get(2).getCost());
    }

    private List<CompanyServiceCostSum> buildAggregateList() {
        Service antivirus = ServiceFactory.builder()
                .name("Mac Antivirus")
                .price(new BigDecimal("7"))
                .build();
        CompanyServiceCostSumStub antivirusAggregate = new CompanyServiceCostSumStub(Long.valueOf("7"), antivirus);

        Service cloudberry = ServiceFactory.builder()
                .name("Cloudberry")
                .price(new BigDecimal("3"))
                .build();
        CompanyServiceCostSumStub cloudberryAggregate = new CompanyServiceCostSumStub(Long.valueOf("3"), cloudberry);

        Service psa = ServiceFactory.builder()
                .name("PSA")
                .price(new BigDecimal("2"))
                .build();
        CompanyServiceCostSumStub psaAggregate = new CompanyServiceCostSumStub(Long.valueOf("2"), psa);

        return Arrays.asList(antivirusAggregate, cloudberryAggregate, psaAggregate);
    }
}
