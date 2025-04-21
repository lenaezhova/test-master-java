package com.testmaster.repository.QuestionRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testmasterapi.domain.question.converter.AnswerTemplateListConverter;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public int update(Long questionId,  QuestionUpdateRequest request) {
        try {
            var query = """
                update questions set title = coalesce(:title, title),
                                     description = coalesce(:description, description),
                                     answer_templates = coalesce(:answer_templates, answer_templates),
                                     soft_deleted = coalesce(:soft_deleted, soft_deleted),
                                     updated_at = coalesce(:updated_at, updated_at)
                where id = :qid and soft_deleted = false
                """;
            LocalDateTime now = LocalDateTime.now();

            var params = new MapSqlParameterSource()
                    .addValue("title", request.getTitle())
                    .addValue("description", request.getDescription())
                    .addValue("answer_templates", mapper.writeValueAsString(request.getAnswerTemplates()))
                    .addValue("soft_deleted", request.isSoftDeleted())
                    .addValue("updated_at", now)
                    .addValue("qid", questionId);

            return jdbcTemplate.update(query, params);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing list", e);
        }
    }
}
