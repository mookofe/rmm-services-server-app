package me.victorcruz.ninjaserver.api.v1.controllers;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import me.victorcruz.ninjaserver.IntegrationTest;
import org.springframework.test.web.servlet.MockMvc;
import me.victorcruz.ninjaserver.utils.ResourceUtils;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.types.DeviceType;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import me.victorcruz.ninjaserver.factories.DeviceServiceFactory;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;
import me.victorcruz.ninjaserver.domain.repositories.ServiceRepository;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class DeviceServiceControllerIntegTest  extends IntegrationTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";
    private static final String DEVICE_ID = "e44e32ca-2e7f-4dd2-b995-d60252377955";
    private static final String ADD_DEVICE_SERVICE_PATH = "/api/v1/companies/%s/devices/%s/services";
    private static final String DELETE_DEVICE_SERVICE_PATH = "/api/v1/companies/%s/devices/%s/services/%s";

    @Autowired
    private MockMvc mockedMvc;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private ServiceRepository serviceRepository;

    @MockBean
    private DeviceServiceRepository deviceServiceRepository;

    @Test
    void testItCanAddServiceToDevice() throws Exception {
        // Given
        Device persistedDevice = DeviceFactory.any();
        Service persistedService = ServiceFactory.any();

        String url = String.format(ADD_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID);
        String payload = ResourceUtils.readFile("requests/device-service-add.json");

        when(deviceRepository.findByCompanyIdAndId(COMPANY_ID, DEVICE_ID)).thenReturn(persistedDevice);
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(persistedService));
        doAnswer(returnsFirstArg()).when(deviceServiceRepository).save(any());

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value(persistedService.getId()))
                .andExpect(jsonPath("$.serviceName").value(persistedService.getName()))
                .andExpect(jsonPath("$.serviceDescription").value(persistedService.getDescription()))
                .andExpect(jsonPath("$.purchasedAt").exists());
    }

    @Test
    void testItReturn422WhenDeviceWasNotFound() throws Exception {
        // Given
        String url = String.format(ADD_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID);
        String payload = ResourceUtils.readFile("requests/device-service-add.json");

        when(deviceRepository.findByCompanyIdAndId(COMPANY_ID, DEVICE_ID)).thenReturn(null);

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Not able to add service to device. Device was not found"))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void testItReturn422WhenServiceWasNotFound() throws Exception {
        // Given
        String url = String.format(ADD_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID);
        String payload = ResourceUtils.readFile("requests/device-service-add.json");

        when(deviceRepository.findByCompanyIdAndId(COMPANY_ID, DEVICE_ID)).thenReturn(DeviceFactory.any());
        when(serviceRepository.findById(anyString())).thenReturn(Optional.empty());

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Not able to add service to device. Service was not found"))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void testItReturn409WhenServiceAlreadyExistsForDevice() throws Exception {
        // Given
        String url = String.format(ADD_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID);
        String payload = ResourceUtils.readFile("requests/device-service-add.json");
        DeviceService persistedDeviceService = DeviceService.builder()
                .build();

        when(deviceRepository.findByCompanyIdAndId(COMPANY_ID, DEVICE_ID)).thenReturn(DeviceFactory.any());
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(ServiceFactory.any()));
        when(deviceServiceRepository.findByDeviceIdAndServiceId(anyString(), anyString())).thenReturn(persistedDeviceService);

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(containsString("already exist for device")))
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void testItReturn422WhenServiceIsNotCompatibleWithDevice() throws Exception {
        // Given
        Device persistedDevice = DeviceFactory.builder()
                .type(DeviceType.WINDOWS_SERVER)
                .build();
        Service persistedService = ServiceFactory.builder()
                .supportsWindows(false)
                .build();

        String url = String.format(ADD_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID);
        String payload = ResourceUtils.readFile("requests/device-service-add.json");

        when(deviceRepository.findByCompanyIdAndId(COMPANY_ID, DEVICE_ID)).thenReturn(persistedDevice);
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(persistedService));

        // When / Then
        mockedMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Device Operating System is not compatible with the device"))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void testItShouldDeleteDeviceServiceSuccessfully() throws Exception {
        // Given
        DeviceService persistedDeviceService = DeviceServiceFactory.any();
        when(deviceServiceRepository.findByIdAndDeviceId(any(), any())).thenReturn(persistedDeviceService);

        String url = String.format(DELETE_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID, persistedDeviceService.getId());

        // When / Then
        mockedMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    void testItShouldReturn404WhenDeviceServiceWasNotFound() throws Exception {
        // Given
        when(deviceServiceRepository.findByIdAndDeviceId(any(), any())).thenReturn(null);

        String url = String.format(DELETE_DEVICE_SERVICE_PATH, COMPANY_ID, DEVICE_ID, "non-existing");

        // When / Then
        mockedMvc.perform(delete(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Service for device was not found"))
                .andExpect(jsonPath("$.status").value(404));;
    }
}
