package org.rentifytools.dto.userDto;

import lombok.*;
import org.rentifytools.dto.addressDto.AddressResponseDto;
import org.rentifytools.dto.roleDto.RoleResponseDto;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressResponseDto address;
    private Set<RoleResponseDto> roles;
}
