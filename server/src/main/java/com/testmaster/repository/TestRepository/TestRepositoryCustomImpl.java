package com.testmaster.repository.TestRepository;

import com.testmasterapi.domain.test.request.TestUpdateRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestRepositoryCustomImpl implements TestRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TestRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int update(Long testId, TestUpdateRequest request) {
        var query = """
                update tests set title = coalesce(:title, title),
                                 description = coalesce(:description, description),
                                 status = coalesce(:status, status)
                where id = :tid
                """;
        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("description", request.getDescription())
                .addValue("status", request.getStatus())
                .addValue("tid", testId);;

        return jdbcTemplate.update(query, params);
    }
}
