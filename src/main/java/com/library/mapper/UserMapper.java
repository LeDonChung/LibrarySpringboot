package com.library.mapper;

import com.library.dto.UserDto;
import com.library.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setFullName(dto.getFullName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        return entity;
    }

    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        if(entity.getRoles() != null) {
            dto.setRoles(entity.getRoles());
        }
        return dto;
    }
}
