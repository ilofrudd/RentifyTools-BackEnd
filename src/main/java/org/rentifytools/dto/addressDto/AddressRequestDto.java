package org.rentifytools.dto.addressDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequestDto {
    private String country;
    private String zipCode;
    private String city;
    private String street;
}
