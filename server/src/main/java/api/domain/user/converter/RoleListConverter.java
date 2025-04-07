package api.domain.user.converter;

import api.domain.user.UserRoles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;


@Converter
public class RoleListConverter implements AttributeConverter<List<UserRoles>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(List<UserRoles> attribute) {
        return attribute.stream().map(Enum::name).toArray(String[]::new);
    }

    @Override
    public List<UserRoles> convertToEntityAttribute(String[] dbData) {
        return Arrays.stream(dbData).map(UserRoles::valueOf).toList();
    }
}