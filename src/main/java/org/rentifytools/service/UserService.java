package org.rentifytools.service;

import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;

import java.util.List;

public interface UserService {

    void checkEmail(UserLoginDto dto);

    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getUsersByLastname(String lastname);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto getUserByPhone(String phone);

    UserResponseDto setRole(Long id, String title);

    UserResponseDto deleteUser(Long id);
}
