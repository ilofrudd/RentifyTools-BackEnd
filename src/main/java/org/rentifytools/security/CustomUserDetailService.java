package org.rentifytools.security;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.User;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        return user.map(CustomUserDetails::new).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }
}
