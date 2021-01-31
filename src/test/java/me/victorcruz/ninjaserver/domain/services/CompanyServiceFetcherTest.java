package me.victorcruz.ninjaserver.domain.services;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import me.victorcruz.ninjaserver.domain.repositories.DeviceServiceRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceFetcherTest {
    private static final String COMPANY_ID = "d604ce24-a75e-482f-baee-eefac1d462ab";

    private CompanyServiceFetcher sut;

    @Mock
    private DeviceServiceRepository deviceServiceRepository;

    @BeforeEach
    void setUp() {
        sut = new CompanyServiceFetcher(deviceServiceRepository);
    }

    @Test
    void testItGetCompanyServicesById() {
        // When
        sut.fetch(COMPANY_ID);

        // Then
        verify(deviceServiceRepository, times(1)).getCompanyServicesById(COMPANY_ID);
    }
}
