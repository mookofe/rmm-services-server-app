package me.victorcruz.ninjaserver.domain.services;

import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import me.victorcruz.ninjaserver.domain.models.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import me.victorcruz.ninjaserver.domain.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsFetcher implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUsername(username);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());
    }
}
