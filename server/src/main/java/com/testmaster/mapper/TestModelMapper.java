package com.testmaster.mapper;

import com.testmaster.dto.TestDto;
import com.testmaster.model.TestModel.TestModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TestModelMapper {
    TestDto toDto(TestModel test);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget TestModel target, TestModel source);
}
