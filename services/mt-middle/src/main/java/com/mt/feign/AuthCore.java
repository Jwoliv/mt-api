package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.model_dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "auth",
        url = "${mt-middle.endpoints.auth}",
        configuration = FeignConfig.class
)
public interface AuthCore {
    @PostMapping("/sign-up")
    UserDto signUp(@RequestBody SignUpDto signUp);
    @PostMapping("/login")
    UserDto login(@RequestBody LoginDto login);
}
