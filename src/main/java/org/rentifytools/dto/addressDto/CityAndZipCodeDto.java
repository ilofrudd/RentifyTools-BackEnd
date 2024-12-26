package org.rentifytools.dto.addressDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityAndZipCodeDto {
    private String city;
    private String zipCode;
}
