package com.testmaster.repository.RoleRepository;

import com.testmaster.model.RoleModel.RoleModel;
import com.testmaster.model.RoleModel.RolesUserModel;
import com.testmaster.model.UserModel.UserModel;
import com.testmaster.model.UserModel.UserRolesId;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RolesUserRepository rolesUserRepository;

    @Test
    public void testSaveRolesUserModel() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        RoleModel role = new RoleModel(
                "Админ"
        );
        entityManager.persist(role);

        UserRolesId id = new UserRolesId(
                user.getId(),
                role.getId()
        );

        RolesUserModel link = new RolesUserModel(
                id,
                user,
                role
        );

        RolesUserModel saved = rolesUserRepository.save(link);

        assertNotNull(saved.getId());
        assertEquals(user.getId(), saved.getUser().getId());
        assertEquals(role.getId(), saved.getRole().getId());
    }

    @Test
    public void testSaveAndFindRolesUserModel() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        RoleModel role = new RoleModel(
                "Пользователь"
        );
        entityManager.persist(role);

        UserRolesId id = new UserRolesId();
        id.setUserId(user.getId());
        id.setRoleId(role.getId());

        RolesUserModel link = new RolesUserModel(
                id,
                user,
                role
        );
        entityManager.persist(link); Optional<RolesUserModel> foundOpt = rolesUserRepository.findById(id);


        assertTrue(foundOpt.isPresent());

        RolesUserModel found = foundOpt.get();
        assertEquals("Пользователь", found.getRole().getTitle());
        assertEquals("Иван Иванов", found.getUser().getName());
    }
}