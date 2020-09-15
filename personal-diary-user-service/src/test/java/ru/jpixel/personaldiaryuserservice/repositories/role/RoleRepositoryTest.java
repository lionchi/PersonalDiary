package ru.jpixel.personaldiaryuserservice.repositories.role;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jpixel.personaldiaryuserservice.domain.Role;
import ru.jpixel.personaldiaryuserservice.repositories.BaseRepositoryTest;
import ru.jpixel.personaldiaryuserservice.repositories.RoleRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Поиск роли по ее наименованию")
    public void findByNameTest() {
        var role = new Role();
        role.setId(1L);
        role.setName("TEST");
        testEntityManager.persist(role);
        assertNotNull(roleRepository.findByName("TEST"));
    }
}
