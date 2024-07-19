package com.mt.service.impl;

import com.mt.dto.UserDto;
import lombok.Setter;
import com.mt.mapper.UserMapper;
import com.mt.model.User;
import com.mt.repository.UserRepository;
import com.mt.service.UserServiceI;
import com.mt.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {

    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserMapper userMapper;
    @Setter(onMethod = @__({@Autowired}))
    private AuthUtils authUtils;

    @Override
    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        var user = userRepository.findByUsername(username).orElse(null);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto signUp(User newUser) {
        var email = newUser.getEmail();
        var username = newUser.getUsername();
        if (userRepository.notExistsByEmailOrUsername(email, username)) {
            var savedUser = userRepository.save(newUser);
            userRepository.save(newUser);
            return userMapper.mapToDto(savedUser);
        }
        return null;
    }
}
