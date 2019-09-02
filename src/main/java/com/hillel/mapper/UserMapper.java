package com.hillel.mapper;

import com.hillel.dto.DeleteUserDto;
import com.hillel.dto.UserDto;
import com.hillel.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
}
