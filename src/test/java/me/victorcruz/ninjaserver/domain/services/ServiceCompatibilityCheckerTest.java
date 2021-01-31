package me.victorcruz.ninjaserver.domain.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.models.Service;
import me.victorcruz.ninjaserver.domain.types.DeviceType;
import me.victorcruz.ninjaserver.domain.exceptions.NotCompatibleServiceException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ServiceCompatibilityCheckerTest {
    private ServiceCompatibilityChecker sut;

    @BeforeEach
    void setUp() {
        sut = new ServiceCompatibilityChecker();
    }

    @Test
    void testItValidatesMacOs() {
        // Given
        Device device = Device.builder()
                .type(DeviceType.MAC)
                .build();

        Service service = Service.builder()
                .supportsMac(true)
                .build();

        // When / Then
        sut.checkOperatingSystem(service, device);
    }

    @Test
    void testItThrowErrorWhenMacOsIsUsingInvalidService() {
        // Given
        Device device = Device.builder()
                .type(DeviceType.MAC)
                .build();

        Service service = Service.builder()
                .supportsMac(false)
                .build();

        // When / Then
        assertThrows(NotCompatibleServiceException.class, () -> sut.checkOperatingSystem(service, device));
    }

    @Test
    void testItValidatesWindows() {
        // Given
        Device device = Device.builder()
                .type(DeviceType.WINDOWS_SERVER)
                .build();

        Service service = Service.builder()
                .supportsWindows(true)
                .build();

        // When / Then
        sut.checkOperatingSystem(service, device);
    }

    @Test
    void testItThrowErrorWhenWindowsIsUsingInvalidService() {
        // Given
        Device device = Device.builder()
                .type(DeviceType.WINDOWS_WORK_STATION)
                .build();

        Service service = Service.builder()
                .supportsWindows(false)
                .build();

        // When / Then
        assertThrows(NotCompatibleServiceException.class, () -> sut.checkOperatingSystem(service, device));
    }
}
