package me.victorcruz.ninjaserver.domain.repositories;

import org.springframework.stereotype.Repository;
import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Device Repository Interface
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {
}
