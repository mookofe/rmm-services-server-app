package me.victorcruz.ninjaserver.domain.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import me.victorcruz.ninjaserver.domain.models.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Device Repository Interface
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {
    List<Device> findByCompanyId(String companyId);
}
