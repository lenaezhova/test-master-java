package com.testmaster.mapper.user;

import com.testmaster.dto.UserDto;
import com.testmaster.model.UserModel.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel user);
}
