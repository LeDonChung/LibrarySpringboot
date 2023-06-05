package com.library.service.impl;

import com.library.dto.UserDto;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.mapper.UserMapper;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto findByUsername(String username) {
        UserDto dto = null;
        try {
            User entity = userRepository.findByUsername(username);
            if(entity != null) {
                dto = userMapper.toDto(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public UserDto save(UserDto userDto) {
        userDto.setRole("USER");
        userDto.setEmail(userDto.getEmail());
        User user = insertUser(userDto);
        return userMapper.toDto(userRepository.save(user));
    }


    private User insertUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        // Mã hóa
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(userDto.getRole()));
        user.setRoles(roles);
        return user;
    }

}
