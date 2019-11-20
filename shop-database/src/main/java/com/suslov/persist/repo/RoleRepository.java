package com.suslov.persist.repo;

import com.suslov.persist.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> getRoleByName(String name);
}

