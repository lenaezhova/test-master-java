package com.testmasterapi.domain.question.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testmasterapi.domain.question.AnswerVariant;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class AnswerVariantListConverter implements AttributeConverter<List<AnswerVariant>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<AnswerVariant> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing list", e);
        }
    }

    @Override
    public List<AnswerVariant> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<AnswerVariant>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing list", e);
        }
    }
}