package com.mt.utils;

import com.mt.dto.security.SignUpDto;
import com.mt.enums.Role;
import lombok.Setter;
import com.mt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Collections;


@Component
public class AuthUtils {

    @Setter(onMethod = @__({@Autowired}))
    private PasswordEncoder passwordEncoder;

    public User buildNewUser(SignUpDto signUp) {
        return User.builder()
                .username(signUp.getUsername())
                .firstname(signUp.getFirstname())
                .lastname(signUp.getLastname())
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .roles(Collections.singletonList(Role.USER))
                .build();
    }

    public Boolean isValidLoginPassword(String loginPassword, String dbPassword) {
        return passwordEncoder.matches(CharBuffer.wrap(loginPassword), dbPassword);
    }
}
