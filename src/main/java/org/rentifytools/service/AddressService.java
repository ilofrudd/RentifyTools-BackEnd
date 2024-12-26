package org.rentifytools.service;

import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.addressDto.AddressResponseDto;
import org.rentifytools.dto.addressDto.CityAndZipCodeDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto addAddress(AddressRequestDto address);

    AddressResponseDto updateAddress(Long id, AddressRequestDto address);

    AddressResponseDto deleteAddress(Long id);

    List<AddressResponseDto> getAddresses();

    List<CityAndZipCodeDto> getAllCityAndZipCodes();
}
