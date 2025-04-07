package com.testmaster.mapper;

import com.testmaster.dto.TestDto;
import com.testmaster.model.TestModel;
import org.mapstruct.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestDto toDto(TestModel test);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget TestModel target, TestModel source);

    @AfterMapping
    default void afterUpdate(@MappingTarget TestModel target, TestModel source) {
        String title = source.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле title не может быть пустым");
        }

        target.setTitle(title);
    }
}
