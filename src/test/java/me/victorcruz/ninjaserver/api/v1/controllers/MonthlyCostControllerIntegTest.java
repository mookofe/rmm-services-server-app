package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.List;
import java.util.Arrays;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import me.victorcruz.ninjaserver.IntegrationTest;
import org.springframework.test.web.servlet.MockMvc;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import me.victorcruz.ninjaserver.doubles.CompanyServiceCostSumStub;
import org.springframework.security.test.context.support.WithMockUser;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCostSum;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class MonthlyCostControllerIntegTest  extends IntegrationTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";
    private static final String COMPANY_MONTHLY_COST_PATH = "/api/v1/companies/%s/monthly-cost";

    @Autowired
    private MockMvc mockedMvc;

    @MockBean
    private DeviceServiceRepository deviceServiceRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @Test
    void testItReturn403WhenUserIsNotAuthorized() throws Exception {
        // Given
        String url = String.format(COMPANY_MONTHLY_COST_PATH, COMPANY_ID);

        // When / Then
        mockedMvc.perform(post(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("test-admin")
    void testItReturnListOfCompanyServices() throws Exception {
        // Given
        List<CompanyServiceCostSum> aggregates = buildAggregateList();
        when(deviceServiceRepository.getCompanyMonthlyCost(COMPANY_ID)).thenReturn(aggregates);
        when(deviceRepository.findByCompanyId(COMPANY_ID)).thenReturn(DeviceFactory.list());

        String url = String.format(COMPANY_MONTHLY_COST_PATH, COMPANY_ID);

        // When / Then
        mockedMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(18))
                .andExpect(jsonPath("$.services", hasSize(3)))
                .andExpect(jsonPath("$.services[0].serviceName").value("Mac Antivirus"))
                .andExpect(jsonPath("$.services[0].cost").value(7.00))
                .andExpect(jsonPath("$.services[1].serviceName").value("Cloudberry"))
                .andExpect(jsonPath("$.services[1].cost").value(3.00));
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

        return Arrays.asList(antivirusAggregate, cloudberryAggregate);
    }
}
