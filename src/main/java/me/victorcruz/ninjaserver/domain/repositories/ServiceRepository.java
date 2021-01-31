package me.victorcruz.ninjaserver.domain.repositories;

import org.springframework.stereotype.Repository;
import me.victorcruz.ninjaserver.domain.models.Service;
import org.springframework.data.repository.CrudRepository;

/**
 * Service Repository Interface
 */
@Repository
public interface ServiceRepository extends CrudRepository<Service, String> {
}
