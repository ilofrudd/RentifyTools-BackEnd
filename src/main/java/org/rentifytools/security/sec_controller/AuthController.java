package org.rentifytools.security.sec_controller;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.entity.User;
import org.rentifytools.security.sec_dto.RefreshRequestDto;
import org.rentifytools.security.sec_dto.TokenResponseDto;
import org.rentifytools.security.sec_services.AuthService;
import org.rentifytools.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Value("${time.refresh}")
    private int refreshTokenDays;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto user, HttpServletResponse response) {
        try {
            TokenResponseDto tokenResponse = authService.login(user);

            Cookie cookie = new Cookie("refreshToken", tokenResponse.getRefreshToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(refreshTokenDays * 60 * 60 * 24);
            response.addCookie(cookie);

            return ResponseEntity.ok(tokenResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
        public TokenResponseDto getNewAccessToken(HttpServletRequest request) {
        String refreshToken = WebUtils.getCookie(request, "refreshToken").getValue();
        return authService.getNewAccessToken(refreshToken);
    }


    @GetMapping("/profile")
    public UserResponseDto getProfile() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return  userService.getUserByEmail(email);
    }
}
