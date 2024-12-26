package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.addressDto.AddressResponseDto;
import org.rentifytools.dto.addressDto.CityAndZipCodeDto;
import org.rentifytools.entity.Address;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public AddressResponseDto addAddress(AddressRequestDto dto) {
        Address address = mapper.map(dto, Address.class);
        Address savedAddress = addressRepository.save(address);
        return mapper.map(savedAddress, AddressResponseDto.class);
    }

    @Override
    public List<AddressResponseDto> getAddresses() {
        return addressRepository.findAll().stream()
                .map(address -> mapper.map(address, AddressResponseDto.class)).collect(Collectors.toList());
    }

    public Address findAddressById(Long id) {
        String exceptionMessage = "Address with ID %d not found";
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, id)));
    }

    @Override
    @Transactional
    public AddressResponseDto updateAddress(Long id, AddressRequestDto dto) {
        Address foundAddress = findAddressById(id);
        mapper.map(dto, foundAddress);
        return mapper.map(addressRepository.save(foundAddress), AddressResponseDto.class);
    }

    @Override
    @Transactional
    public AddressResponseDto deleteAddress(Long id) {
//        Address address = findAddressById(id);
//        addressRepository.deleteById(id);
        String exceptionMessage = "Address with ID %d not found";
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, id)));
        addressRepository.deleteById(id);
        return mapper.map(address, AddressResponseDto.class);
    }

    @Override
    public List<CityAndZipCodeDto> getAllCityAndZipCodes() {
        return addressRepository.findAllCityAndZipCodes();

    }
}
