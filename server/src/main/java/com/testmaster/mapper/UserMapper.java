package com.testmaster.mapper;

import com.testmaster.dto.UserDto;
import com.testmaster.model.TestModel.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel user);
}
