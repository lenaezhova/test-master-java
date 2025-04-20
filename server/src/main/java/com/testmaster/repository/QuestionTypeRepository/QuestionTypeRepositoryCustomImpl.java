package com.testmaster.repository.QuestionTypeRepository;

import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionTypeRepositoryCustomImpl implements QuestionTypeRepositoryCustom {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int update(Long typeId, QuestionTypeUpdateRequest request) {
        var query = """
        update questions_types set title = coalesce(:title, title),
                                   types = coalesce(:types, types)
        where id = :tid
        """;

        String[] typesArray = null;
        if (request.getTypes() != null) {
            typesArray = request.getTypes().stream().map(Enum::name).toArray(String[]::new);
        }

        var params = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("types", typesArray)
                .addValue("tid", typeId);

        return jdbcTemplate.update(query, params);
    }
}
