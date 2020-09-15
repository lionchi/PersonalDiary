package ru.jpixel.personaldiaryuserservice.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
