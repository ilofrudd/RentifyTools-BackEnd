package org.rentifytools.security.sec_services;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.security.CustomUserDetailService;
import org.rentifytools.security.CustomUserDetails;
import org.rentifytools.security.sec_dto.TokenResponseDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Map<String, String> refreshTokensStorage = new HashMap<>();

    public TokenResponseDto login(UserLoginDto inboundUser) throws AuthException {
        String email = inboundUser.getEmail();
        CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(email);

        if (passwordEncoder.matches(inboundUser.getPassword(), userDetails.getPassword())) {
            String accessToken = tokenService.generateAccessToken(userDetails);
            String refreshToken = tokenService.generateRefreshToken(userDetails);
            refreshTokensStorage.put(email, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid email or password");
        }
    }

    public TokenResponseDto getNewAccessToken(String inboundRefreshToken) {
        Claims refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);
        String email = refreshClaims.getSubject();
        String savedRefreshToken = refreshTokensStorage.get(email);

        if (savedRefreshToken != null && savedRefreshToken.equals(inboundRefreshToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(email);
            String accessToken = tokenService.generateAccessToken(userDetails);
            return new TokenResponseDto(accessToken, inboundRefreshToken);
        } else {
            return new TokenResponseDto(null, null);
        }
    }
}
