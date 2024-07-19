package com.mt.mapper;

import com.mt.dto.UserDto;
import com.mt.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToDto(User user);
}
