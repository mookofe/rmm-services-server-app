package me.victorcruz.ninjaserver.domain.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import me.victorcruz.ninjaserver.domain.models.DeviceService;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCount;
import me.victorcruz.ninjaserver.domain.aggregations.CompanyServiceCostSum;

@Repository
public interface DeviceServiceRepository extends CrudRepository<DeviceService, String> {
    DeviceService findByDeviceIdAndServiceId(String serviceId, String deviceId);
    DeviceService findByIdAndDeviceId(String id, String deviceId);

    @Query("SELECT s as service, COUNT(*) as totalDevices from DeviceService ds JOIN ds.service s JOIN ds.device d WHERE d.companyId = ?1  GROUP BY s")
    List<CompanyServiceCount> getCompanyServicesById(String companyId);

    @Query("SELECT s as service, SUM(s.price) as totalCost from DeviceService ds JOIN ds.service s JOIN ds.device d WHERE d.companyId = ?1  GROUP BY s")
    List<CompanyServiceCostSum> getCompanyMonthlyCost(String companyId);
}
