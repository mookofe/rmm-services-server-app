package me.victorcruz.ninjaserver.api.v1.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import me.victorcruz.ninjaserver.IntegrationTest;
import org.springframework.test.web.servlet.MockMvc;
import me.victorcruz.ninjaserver.utils.ResourceUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class DeviceControllerIntegTest extends IntegrationTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";
    private static final String CREATE_DEVICE_PATH = "/api/v1/companies/%s/devices";

    @Autowired
    private MockMvc mockedMvc;

    @MockBean
    private DeviceRepository deviceRepository;

    @Test
    void testItCreateDeviceSuccessfully() throws Exception {
        // Given
        String url = String.format(CREATE_DEVICE_PATH, COMPANY_ID);
        String payload = ResourceUtils.readFile("requests/device-create.json");

        doAnswer(returnsFirstArg()).when(deviceRepository).save(any());

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(COMPANY_ID))
                .andExpect(jsonPath("$.systemName").value("System name"))
                .andExpect(jsonPath("$.type").value("MAC"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void testItReturnsValidationErrorWhenPayloadIsInvalid() throws Exception  {
        // Given
        String url = String.format(CREATE_DEVICE_PATH, COMPANY_ID);
        String payload = "{}";

        // When / THen
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray());
    }
}
