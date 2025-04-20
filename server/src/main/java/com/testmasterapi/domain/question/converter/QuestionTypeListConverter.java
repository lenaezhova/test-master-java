package com.testmasterapi.domain.question.converter;

import com.testmasterapi.domain.question.QuestionTypes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class QuestionTypeListConverter implements AttributeConverter<Set<QuestionTypes>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(Set<QuestionTypes> attribute) {
        if (attribute == null) return new String[0];
        return attribute.stream()
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @Override
    public Set<QuestionTypes> convertToEntityAttribute(String[] dbData) {
        if (dbData == null) return Collections.emptySet();
        return Arrays.stream(dbData)
                .map(QuestionTypes::valueOf)
                .collect(Collectors.toSet());
    }
}