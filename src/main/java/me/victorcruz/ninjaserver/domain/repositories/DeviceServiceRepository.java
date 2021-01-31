package me.victorcruz.ninjaserver.domain.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import me.victorcruz.ninjaserver.domain.models.DeviceService;

@Repository
public interface DeviceServiceRepository extends CrudRepository<DeviceService, String> {
    DeviceService findByDeviceIdAndServiceId(String serviceId, String deviceId);
    DeviceService findByIdAndDeviceId(String id, String deviceId);
}
