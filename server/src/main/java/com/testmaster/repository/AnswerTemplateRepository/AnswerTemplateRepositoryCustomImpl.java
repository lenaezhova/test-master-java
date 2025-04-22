package com.testmaster.repository.AnswerTemplateRepository;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerTemplateRepositoryCustomImpl implements AnswerTemplateRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int update(Long answerTemplateId, AnswerTemplateUpdateRequest request) {
        var query = """
                update answers_templates set text = coalesce(:text, text),
                                             count_points = coalesce(:count_points, count_points),
                                             is_correct = coalesce(:is_correct, is_correct),
                                             description = coalesce(:description, description)
                where id = :atid
                """;

        var params = new MapSqlParameterSource()
                .addValue("text", request.getText())
                .addValue("description", request.getDescription())
                .addValue("count_points", request.getCountPoints())
                .addValue("is_correct", request.getIsCorrect())
                .addValue("atid", answerTemplateId);

        return jdbcTemplate.update(query, params);
    }
}
