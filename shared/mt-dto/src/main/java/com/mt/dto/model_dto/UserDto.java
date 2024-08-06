package com.mt.dto.model_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
@ToString
public class UserDto {
    public Long id;
    @Email
    public String email;
    @NotBlank
    public String username;
    @NotBlank
    public String firstname;
    @NotBlank
    public String lastname;
    public List<Role> roles;
    public String token;

    public enum Role implements GrantedAuthority {
        MAIN_ADMIN,
        ADMIN,
        SUPPORT,
        MODERATOR,
        USER;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }

}