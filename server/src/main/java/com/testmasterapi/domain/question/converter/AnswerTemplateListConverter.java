package com.testmasterapi.domain.question.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.testmasterapi.domain.question.AnswerTemplate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Converter
@Component
public class AnswerTemplateListConverter implements AttributeConverter<List<AnswerTemplate>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<AnswerTemplate> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing list", e);
        }
    }

    @Override
    public List<AnswerTemplate> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<AnswerTemplate>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing list", e);
        }
    }
}