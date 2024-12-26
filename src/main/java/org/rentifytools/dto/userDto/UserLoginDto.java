package org.rentifytools.dto.userDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginDto {
    private String email;
    private String password;
}
