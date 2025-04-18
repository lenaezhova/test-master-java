package com.testmaster.repository.GroupRepository;

import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GroupRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int update(Long groupId, GroupUpdateRequest request) {
        var query = """
                update groups set title = coalesce(:title, title)
                where id = :gid
                """;
        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("gid", groupId);;

        return jdbcTemplate.update(query, params);
    }
}
