package com.mt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mt.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.mt.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider {
    public static final String ROLES = "roles";
    public static final Integer LIVING_TIME_TOKEN = 3_600_000;


    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Setter(onMethod = @__({@Autowired}))
    private UserServiceI userService;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public void setTokenToUser(UserDto user) {
        var token = createToken(user);
        user.setToken(token);
    }

    public Authentication validateToken(String token) {
        var decoded = getDecoded(token);
        var user = buildEmployeeToValidateToken(decoded);
        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }

    public Authentication validateTokenStrongly(String token) {
        var decoded = getDecoded(token);
        var employee = userService.findByEmail(decoded.getSubject());
        return new UsernamePasswordAuthenticationToken(employee, null, employee.getRoles());
    }


    public String createToken(UserDto user) {
        var now = new Date();
        var validity = new Date(now.getTime() + LIVING_TIME_TOKEN);
        var algorithm = Algorithm.HMAC256(secretKey);
        return buildJwtToken(user, now, validity, algorithm);
    }

    public String extractEmail(String token) {
        DecodedJWT decodedJWT = getDecoded(token);
        return decodedJWT.getSubject();
    }

    private DecodedJWT getDecoded(String token) {
        var algorithm = Algorithm.HMAC256(secretKey);
        var verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private UserDto buildEmployeeToValidateToken(DecodedJWT decoded) {
        return UserDto.builder()
                .email(decoded.getSubject())
                .roles(decoded.getClaim(ROLES).asList(UserDto.Role.class))
                .build();
    }

    private String buildJwtToken(UserDto user, Date now, Date validity, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim(ROLES, convertRolesToListString(user))
                .sign(algorithm);
    }

    private List<String> convertRolesToListString(UserDto user) {
        return user.getRoles().stream()
                .map(Enum::name)
                .toList();
    }

}