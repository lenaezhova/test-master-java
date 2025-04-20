package com.testmaster.repository.QuestionRepository;

import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int update(Long questionId,  QuestionUpdateRequest request) {
        var query = """
                update questions set title = coalesce(:title, title),
                                     description = coalesce(:description, description),
                                     saved_type = coalesce(:saved_type, saved_type),
                                     updated_at = coalesce(:updated_at, updated_at)
                where id = :qid
                """;
        LocalDateTime now = LocalDateTime.now();
        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("description", request.getDescription())
                .addValue("saved_type", request.getSavedType().name())
                .addValue("updated_at", now)
                .addValue("qid", questionId);

        return jdbcTemplate.update(query, params);
    }
}
