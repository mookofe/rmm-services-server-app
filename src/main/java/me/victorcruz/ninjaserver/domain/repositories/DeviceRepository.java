package me.victorcruz.ninjaserver.domain.repositories;

import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Device Repository Interface
 */
public interface DeviceRepository extends CrudRepository<Device, String> {
}
