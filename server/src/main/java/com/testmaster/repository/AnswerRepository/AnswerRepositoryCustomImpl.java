package com.testmaster.repository.AnswerRepository;

import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerRepositoryCustomImpl implements AnswerRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int update(Long answerId, AnswerUpdateRequest request) {
        var query = """
                update answers_templates set text = coalesce(:text, text)
                where id = :aid
                """;

        var params = new MapSqlParameterSource()
                .addValue("text", request.getText())
                .addValue("aid", answerId);

        return jdbcTemplate.update(query, params);
    }
}
