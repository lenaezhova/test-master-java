package com.testmaster.repository.UserRepository;

import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int update(Long userId, UserUpdateRequest updateRequest) {
        var query = """
                update users set name = coalesce(:name, name),
                                 activation_link = coalesce(:activation_link, activation_link),
                                 roles = coalesce(:roles, roles),
                                 deleted = coalesce(:deleted, deleted),
                                 updated_at = coalesce(:updated_at, updated_at),
                                 is_activate = coalesce(:is_activate, is_activate)
                where id = :uid and deleted = false
                """;
        LocalDateTime now = LocalDateTime.now();

        String[] typesRoles = null;
        if (updateRequest.getRoles() != null) {
            typesRoles = updateRequest.getRoles().stream().map(Enum::name).toArray(String[]::new);
        }

        var params = new MapSqlParameterSource()
                .addValue("name", updateRequest.getName())
                .addValue("activation_link", updateRequest.getActivationLink())
                .addValue("roles", typesRoles)
                .addValue("deleted", updateRequest.getDeleted())
                .addValue("is_activate", updateRequest.getIsActivate())
                .addValue("updated_at", now)
                .addValue("uid", userId);

        return jdbcTemplate.update(query, params);
    }
}
