package org.rentifytools.dto.userDto;

import lombok.*;
import org.rentifytools.dto.addressDto.AddressResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserSimpleResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressResponseDto address;
}
