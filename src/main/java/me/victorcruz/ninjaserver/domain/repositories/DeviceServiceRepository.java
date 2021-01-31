package me.victorcruz.ninjaserver.domain.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;

@Repository
public interface DeviceServiceRepository extends CrudRepository<DeviceService, String> {
    DeviceService findByDeviceIdAndServiceId(String serviceId, String deviceId);
    DeviceService findByIdAndDeviceId(String id, String deviceId);

    @Query("SELECT s as service, COUNT(*) as totalDevices from DeviceService ds JOIN ds.service s GROUP BY s")
    List<CompanyServiceCount> getCompanyServicesById(String companyId);
}
