package com.testmaster.repository.TestSessionRepository;

import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestSessionRepositoryCustomImpl implements TestSessionRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int update(Long testSessionId, TestSessionUpdateRequest request) {
        var query = """
                update tests_sessions set status = coalesce(:status, status),
                                          count_points = coalesce(:count_points, count_points),
                                          updated_at = coalesce(:updated_at, updated_at),
                                          closed_at = coalesce(:closed_at, closed_at)
                where id = :tsid
                """;
        LocalDateTime now = LocalDateTime.now();

        var params = new MapSqlParameterSource();

        fillUpdateParams(params);

        params.addValue("status", request.getStatus())
              .addValue("count_points", request.getCountPoints())
              .addValue("closed_at", request.getClosedAt())
              .addValue("tsid", testSessionId);

        return jdbcTemplate.update(query, params);
    }

    private void fillUpdateParams(MapSqlParameterSource source) {
        LocalDateTime now = LocalDateTime.now();
        source.addValue("updated_at", now);
    }
}
