package com.mt.controller.auth;

import com.mt.dto.model_dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;
import lombok.Setter;
import com.mt.service.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Setter(onMethod = @__({@Autowired}))
    private AuthServiceI authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto login) {
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUp) {
        return ResponseEntity.ok(authService.singUp(signUp));
    }

}
