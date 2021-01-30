package me.victorcruz.ninjaserver.domain.repositories;

import me.victorcruz.ninjaserver.domain.models.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * Company Repository Interface
 */
public interface CompanyRepository extends CrudRepository<Company, String> {
}
