package com.testmasterapi.domain.user.converter;

import com.testmasterapi.domain.user.UserRoles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleListConverter implements AttributeConverter<Set<UserRoles>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(Set<UserRoles> attribute) {
        if (attribute == null) return new String[0];
        return attribute.stream()
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @Override
    public Set<UserRoles> convertToEntityAttribute(String[] dbData) {
        if (dbData == null) return Collections.emptySet();
        return Arrays.stream(dbData)
                .map(UserRoles::valueOf)
                .collect(Collectors.toSet());
    }
}