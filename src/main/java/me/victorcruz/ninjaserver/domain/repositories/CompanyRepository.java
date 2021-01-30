package me.victorcruz.ninjaserver.domain.repositories;

import org.springframework.stereotype.Repository;
import me.victorcruz.ninjaserver.domain.models.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * Company Repository Interface
 */
@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {
}
