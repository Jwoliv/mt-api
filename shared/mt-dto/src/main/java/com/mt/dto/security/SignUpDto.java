package com.mt.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    @NotBlank
    public String username;
    @NotBlank
    public String firstname;
    @NotBlank
    public String lastname;
    @Email
    private String email;
    @NotBlank
    private String password;
}
