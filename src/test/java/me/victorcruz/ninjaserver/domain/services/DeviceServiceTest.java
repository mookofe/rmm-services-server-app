package me.victorcruz.ninjaserver.domain.services;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import me.victorcruz.ninjaserver.domain.models.Device;
import me.victorcruz.ninjaserver.domain.repositories.DeviceRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";

    private DeviceService sut;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        sut = new DeviceService(deviceRepository);
    }

    @Test
    void testItStoreDevice() {
        // Given
        Device device = Device.builder()
                .build();

        doAnswer(returnsFirstArg()).when(deviceRepository).save(any());

        // When
        Device deviceCreated = sut.store(COMPANY_ID, device);

        // Then
        assertEquals(COMPANY_ID, deviceCreated.getCompanyId());
        assertNotNull(deviceCreated.getCreatedAt());
        assertNotNull(deviceCreated.getUpdatedAt());
    }

    @Test
    void testItReturnListOfDevicesForGivenCompany() {
        // When
        sut.getDevices(COMPANY_ID);

        // Then
        verify(deviceRepository, times(1)).findByCompanyId(COMPANY_ID);
    }
}
