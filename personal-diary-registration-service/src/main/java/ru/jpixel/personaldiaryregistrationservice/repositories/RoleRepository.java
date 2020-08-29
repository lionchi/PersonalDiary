package ru.jpixel.personaldiaryregistrationservice.repositories;


import org.springframework.data.repository.CrudRepository;
import ru.jpixel.personaldiaryregistrationservice.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
