package com.library.service;

import com.library.dto.UserDto;

public interface UserService {
    // Admin
    UserDto findByUsername(String username);
    UserDto save(UserDto userDto);
    // User
    // API

}
