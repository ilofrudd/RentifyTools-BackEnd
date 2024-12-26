package org.rentifytools.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.userDto.SearchRequestDto;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.exception.DuplicateEmailException;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.security.utils.SecurityUtils;
import org.rentifytools.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "User API", description = "Methods for working with users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @Operation(summary = "Checking of exists email")
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody UserLoginDto dto) {
        try {
            service.checkEmail(dto);
            return ResponseEntity.ok(Map.of("message", "Email is available"));
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
    }

    @Operation(summary = "Getting a list of all users")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = service.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Search of parameters (lastname, email, phone)")
    @PostMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsers(@RequestBody SearchRequestDto searchRequest) {
        String lastname = searchRequest.getLastname();
        String email = searchRequest.getEmail();
        String phone = searchRequest.getPhone();
        List<UserResponseDto> users;

        if (isNullOrEmpty(lastname) && isNullOrEmpty(email) && isNullOrEmpty(phone)) {
            users = service.getAllUsers();
        } else {
            users = new ArrayList<>();
            if (!isNullOrEmpty(email)) {
                users.add(service.getUserByEmail(email));
            } else if (!isNullOrEmpty(phone)) {
                users.add(service.getUserByPhone(phone));
            } else if (!isNullOrEmpty(lastname)) {
                users = service.getUsersByLastname(lastname);
            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Getting user by Id")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "userId") Long id) {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }

    @Operation(summary = "Adding new user to the list")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto user) {
        System.out.println("Create user request received: " + user);
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Editing user information")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable(name = "userId") Long userId, @RequestBody UserRequestDto userDto) {
        return new ResponseEntity<>(service.updateUser(userId, userDto), HttpStatus.OK);
    }

    @Operation(summary = "Assign a new role for user")
    @PatchMapping("/{id}")
    public UserResponseDto setRole(@PathVariable(name = "id") Long id, @Valid @RequestParam(name = "role", defaultValue = "USER") String title) {
        return service.setRole(id, title);
    }

    @Operation(summary = "Removing a user from the list")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteMe(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.deleteUser(id), HttpStatus.OK);
    }

    @Operation(summary = "Removing authorized user from the list")
    @DeleteMapping("/me")
    public ResponseEntity<UserResponseDto> deleteMe() {
        System.out.println("Removing authorized user from the list");
        Long userId = SecurityUtils.getCurrentUserId();
        System.out.println("UserId :" + userId);
        return new ResponseEntity<>(service.deleteUser(userId), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> userNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
