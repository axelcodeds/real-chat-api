package dev.axeldiego.realchatapi.mapper;

import dev.axeldiego.realchatapi.dto.UserDto;
import dev.axeldiego.realchatapi.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(UserDto dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }
}

