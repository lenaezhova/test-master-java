package com.testmaster.repository.AnswerVariantRepository;

import com.testmaster.repository.GroupRepository.GroupRepositoryCustom;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnswerVariantRepositoryCustomImpl implements AnswerVariantRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int update(Long answerVariantId, AnswerVariantUpdateRequest request) {
        var query = """
                update answer_variants set title = coalesce(:title, title),
                                           description = coalesce(:description, description),
                                           is_correct = coalesce(:is_correct, is_correct),
                                           count_points = coalesce(:count_points, count_points)
                where id = :awid
                """;
        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("description", request.getDescription())
                .addValue("is_correct", request.getIsCorrect())
                .addValue("count_points", request.getCountPoints())
                .addValue("awid", answerVariantId);;

        return jdbcTemplate.update(query, params);
    }
}
