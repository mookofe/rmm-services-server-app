package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import me.victorcruz.ninjaserver.IntegrationTest;
import org.springframework.test.web.servlet.MockMvc;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.utils.ResourceUtils;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class DeviceControllerIntegTest extends IntegrationTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";
    private static final String CREATE_DEVICE_PATH = "/api/v1/companies/%s/devices";
    private static final String DEVICE_PATH = "/api/v1/companies/%s/devices/%s";

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

    @Test
    void testItReturnsListOfDevices() throws Exception  {
        // Given
        String url = String.format(CREATE_DEVICE_PATH, COMPANY_ID);
        List<Device> devices = DeviceFactory.list();

        when(deviceRepository.findByCompanyId(COMPANY_ID)).thenReturn(devices);

        // When / Then
        Device firstDevice = devices.get(0);

        mockedMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstDevice.getId()))
                .andExpect(jsonPath("$[0].companyId").value(firstDevice.getCompanyId()))
                .andExpect(jsonPath("$[0].systemName").value(firstDevice.getSystemName()))
                .andExpect(jsonPath("$[0].type").value(firstDevice.getType().toString()))
                .andExpect(jsonPath("$[0].createdAt").value("2021-01-30T00:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").value("2021-01-30T00:00:00"));
    }

    @Test
    void testItUpdateDeviceSuccessfully() throws Exception {
        // Given
        Device persistedDevice = DeviceFactory.any();
        String url = String.format(DEVICE_PATH, COMPANY_ID, persistedDevice.getId());
        String payload = ResourceUtils.readFile("requests/device-update.json");

        doAnswer(returnsFirstArg()).when(deviceRepository).save(any());
        when(deviceRepository.findByCompanyIdAndId(any(), any())).thenReturn(persistedDevice);

        // When / Then
        mockedMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(persistedDevice.getId()))
                .andExpect(jsonPath("$.companyId").value(persistedDevice.getCompanyId()))
                .andExpect(jsonPath("$.systemName").value("New system name"))
                .andExpect(jsonPath("$.type").value("WINDOWS_WORK_STATION"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void testItReturn404IfDeviceWasNotFound() throws Exception {
        // Given
        String url = String.format(DEVICE_PATH, COMPANY_ID, "random-id");
        String payload = ResourceUtils.readFile("requests/device-update.json");

        when(deviceRepository.findByCompanyIdAndId(any(), any())).thenReturn(null);

        // When / Then
        String expectedError = "Device with id='random-id' for company id='d604ce24-a75e-482f-baee-eefac1d462ab' was not found";
        mockedMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(expectedError))
                .andExpect(jsonPath("$.status").value(404));
    }
}
