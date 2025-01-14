package org.rentifytools.security.sec_dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
