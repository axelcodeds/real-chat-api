package dev.axeldiego.realchatapi.service;

import dev.axeldiego.realchatapi.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String id);
    List<UserDto> getAllUsers();
    UserDto updateUser(String id, UserDto userDto);
    void deleteUser(String id);
}

