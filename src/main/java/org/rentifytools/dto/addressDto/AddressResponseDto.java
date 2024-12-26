package org.rentifytools.dto.addressDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressResponseDto {
    private String country;
    private String zipCode;
    private String city;
    private String street;
}
