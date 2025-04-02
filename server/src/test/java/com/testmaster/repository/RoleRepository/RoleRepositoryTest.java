package com.testmaster.repository.RoleRepository;

import com.testmaster.model.RoleModel.RoleModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveRoleModel() {
        RoleModel role = new RoleModel(
                "Администратор"
        );

        RoleModel saved = roleRepository.save(role);

        assertNotNull(saved.getId());
        assertEquals("Администратор", saved.getTitle());
    }

    @Test
    public void testFindRoleModel() {
        RoleModel role = new RoleModel(
                "Пользователь"
        );

        roleRepository.save(role);

        Optional<RoleModel> foundOpt = roleRepository.findById(role.getId());

        assertTrue(foundOpt.isPresent());
        RoleModel found = foundOpt.get();
        assertEquals("Пользователь", found.getTitle());
    }
}