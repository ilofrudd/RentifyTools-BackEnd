package org.rentifytools.repository;

import org.rentifytools.dto.addressDto.CityAndZipCodeDto;
import org.rentifytools.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    void deleteById(Long id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.address = :address")
    long countUsersByAddress(Address address);

    @Query("SELECT new org.rentifytools.dto.addressDto.CityAndZipCodeDto(a.city, a.zipCode) FROM Address a")
    List<CityAndZipCodeDto> findAllCityAndZipCodes();
    List<Address> id(Long id);
}
