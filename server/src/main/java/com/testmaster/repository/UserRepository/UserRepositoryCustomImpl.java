package com.testmaster.repository.UserRepository;

import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
                                 activationLink = coalesce(:activationLink, activationLink),
                                 roles = coalesce(:roles, roles),
                                 deleted = coalesce(:deleted, deleted),
                                 isActivate = coalesce(:isActivate, isActivate)
                where id = :uid
                """;
        var params = new MapSqlParameterSource()
                .addValue("name", updateRequest.getName())
                .addValue("activationLink", updateRequest.getActivationLink())
                .addValue("roles", updateRequest.getRoles())
                .addValue("deleted", updateRequest.getDeleted())
                .addValue("isActivate", updateRequest.getIsActivate())
                .addValue("uid", userId);

        return jdbcTemplate.update(query, params);
    }
}
