package com.testmaster.repository.TestRepository;

import com.testmasterapi.domain.test.request.TestUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class TestRepositoryCustomImpl implements TestRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public int update(Long testId, TestUpdateRequest request) {
        var query = """
                update tests set title = coalesce(:title, title),
                                 description = coalesce(:description, description),
                                 updated_at = coalesce(:updated_at, updated_at),
                                 deleted = coalesce(:deleted, deleted),
                                 status = coalesce(:status, status)
                where id = :tid and deleted = false
                """;


        LocalDateTime now = LocalDateTime.now();

        var status = request.getStatus();

        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("description", request.getDescription())
                .addValue("status", status != null ? status.name() : null)
                .addValue("updated_at", now)
                .addValue("deleted", request.getDeleted())
                .addValue("tid", testId);

        return jdbcTemplate.update(query, params);
    }
}
