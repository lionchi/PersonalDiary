package ru.jpixel.personaldiaryuserservice.repositories.secr;

import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryuserservice.domain.secr.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
