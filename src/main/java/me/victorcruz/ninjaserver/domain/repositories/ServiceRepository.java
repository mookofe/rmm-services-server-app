package me.victorcruz.ninjaserver.domain.repositories;

import me.victorcruz.ninjaserver.domain.models.Service;
import org.springframework.data.repository.CrudRepository;

/**
 * Company Repository Interface
 */
public interface ServiceRepository extends CrudRepository<Service, String> {
}
