package me.victorcruz.ninjaserver.domain.services;

import org.mockito.Mock;
import java.util.Optional;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javax.validation.ValidationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.factories.DeviceFactory;
import me.victorcruz.ninjaserver.factories.ServiceFactory;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.factories.DeviceServiceFactory;
import me.victorcruz.ninjaserver.domain.repositories.ServiceRepository;
import me.victorcruz.ninjaserver.domain.exceptions.DeviceNotFoundException;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;
import me.victorcruz.ninjaserver.domain.exceptions.DeviceServiceNotFoundException;
import me.victorcruz.ninjaserver.domain.exceptions.ServiceAlreadyExistForDeviceException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceManagerTest {
    private DeviceServiceManager sut;

    @Mock
    private DeviceManager deviceManager;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private DeviceServiceRepository deviceServiceRepository;

    @Mock
    private ServiceCompatibilityChecker serviceCompatibilityChecker;

    @BeforeEach
    void setUp() {
        sut = new DeviceServiceManager(deviceManager, serviceRepository, deviceServiceRepository, serviceCompatibilityChecker);
    }

    @Test
    void testItAddServiceToDeviceSuccessfully() {
        // Given
        Device device = DeviceFactory.any();
        Service service = ServiceFactory.any();

        when(deviceManager.find(anyString(), anyString())).thenReturn(device);
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(service));
        when(deviceServiceRepository.findByDeviceIdAndServiceId(anyString(), anyString())).thenReturn(null);

        // When
        sut.addService(device.getCompanyId(), device.getId(), service.getId());

        // Then
        verify(serviceCompatibilityChecker, times(1)).checkOperatingSystem(service, device);

        ArgumentCaptor<DeviceService> argumentCaptor = ArgumentCaptor.forClass(DeviceService.class);
        verify(deviceServiceRepository, times(1)).save(argumentCaptor.capture());
        DeviceService savedDeviceService = argumentCaptor.getValue();

        assertEquals(device, savedDeviceService.getDevice());
        assertEquals(service, savedDeviceService.getService());
        assertNotNull(savedDeviceService.getCreatedAt());
    }

    @Test
    void testItThrowValidationExceptionWhenDeviceWasNotFound() {
        // Given
        when(deviceManager.find(any(), any())).thenThrow(new DeviceNotFoundException("company-id", "device-id"));

        // When / then
        assertThrows(
                ValidationException.class,
                () -> sut.addService("company-id", "device-id", "service-id")
        );
    }

    @Test
    void testItThrowValidationExceptionWhenServiceWasNotFound() {
        // Given
        when(deviceManager.find(anyString(), anyString())).thenReturn(DeviceFactory.any());
        when(serviceRepository.findById(any())).thenReturn(Optional.empty());

        // When / then
        assertThrows(
                ValidationException.class,
                () -> sut.addService("company-id", "device-id", "service-id")
        );
    }

    @Test
    void testItThrowExceptionWhenServiceAlreadyExists() {
        // Given
        DeviceService persistedDeviceService = DeviceService.builder()
                .build();

        when(deviceManager.find(anyString(), anyString())).thenReturn(DeviceFactory.any());
        when(serviceRepository.findById(any())).thenReturn(Optional.of(ServiceFactory.any()));
        when(deviceServiceRepository.findByDeviceIdAndServiceId(any(), any())).thenReturn(persistedDeviceService);

        // When / then
        assertThrows(
                ServiceAlreadyExistForDeviceException.class,
                () -> sut.addService("company-id", "device-id", "service-id")
        );
    }

    @Test
    void testItDeleteDeviceServiceSuccessfully() {
        // Given
        DeviceService persistedDeviceService = DeviceServiceFactory.any();
        when(deviceServiceRepository.findByIdAndDeviceId(anyString(), anyString())).thenReturn(persistedDeviceService);

        // When
        sut.deleteService(
                persistedDeviceService.getDevice().getCompanyId(),
                persistedDeviceService.getDevice().getId(),
                persistedDeviceService.getId()
        );

        // Then
        verify(deviceServiceRepository, times(1)).delete(any());
    }

    @Test
    void testItThrowExceptionWhenDeviceServiceWasNotFound() {
        // Given
        when(deviceServiceRepository.findByIdAndDeviceId(anyString(), anyString())).thenReturn(null);

        // When / Then
        assertThrows(
                DeviceServiceNotFoundException.class,
                () -> sut.deleteService("company-id", "device-id", "service-id"),
                "Service for device was not found"
        );
    }
}
