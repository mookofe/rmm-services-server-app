package me.victorcruz.ninjaserver.domain.repositories;

import me.victorcruz.ninjaserver.domain.models.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<ApplicationUser, String> {
    ApplicationUser findByUsername(String username);
}
