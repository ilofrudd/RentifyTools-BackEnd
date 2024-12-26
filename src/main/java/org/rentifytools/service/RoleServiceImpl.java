package org.rentifytools.service;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Role;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role getRole(String title) {
        return repository.findByTitle(title).orElseThrow(() -> new NotFoundException("No role found with title: " + title));
    }
}
