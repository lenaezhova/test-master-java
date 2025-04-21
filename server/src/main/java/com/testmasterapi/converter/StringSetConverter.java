package com.testmasterapi.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        try {
            return mapper.writeValueAsString(set);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting set to JSON", e);
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Set<String>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading JSON from database", e);
        }
    }
}