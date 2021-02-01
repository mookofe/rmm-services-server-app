package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import me.victorcruz.ninjaserver.IntegrationTest;
import org.springframework.test.web.servlet.MockMvc;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class CompanyServiceControllerIntegTest extends IntegrationTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";
    private static final String COMPANY_SERVICES_PATH = "/api/v1/companies/%s/services";

    @Autowired
    private MockMvc mockedMvc;

    @MockBean
    DeviceServiceRepository deviceServiceRepository;

    @Test
    void testItReturn403WhenUserIsNotAuthorized() throws Exception {
        // Given
        String url = String.format(COMPANY_SERVICES_PATH, COMPANY_ID);

        // When / Then
        mockedMvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("test-admin")
    void testItReturnListOfCompanyServices() throws Exception {
        // Given
        CompanyServiceCount companyService = getService();
        Service service = companyService.getService();
        when(deviceServiceRepository.getCompanyServicesById(COMPANY_ID)).thenReturn(Arrays.asList(companyService));

        String url = String.format(COMPANY_SERVICES_PATH, COMPANY_ID);

        // When / Then
        mockedMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("Mac Antivirus"))
                .andExpect(jsonPath("$[0].price").value(7.00))
                .andExpect(jsonPath("$[0].description").value("service description"))
                .andExpect(jsonPath("$[0].numberOfDevices").value(2));
    }

    private CompanyServiceCount getService() {
        return new CompanyServiceCount() {
            @Override
            public Service getService() {
                return ServiceFactory.any();
            }

            @Override
            public Long getTotalDevices() {
                return Long.valueOf(2);
            }
        };
    }
}
