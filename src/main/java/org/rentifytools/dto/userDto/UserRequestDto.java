package org.rentifytools.dto.userDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.rentifytools.dto.addressDto.AddressRequestDto;
import org.rentifytools.dto.addressDto.AddressResponseDto;
import org.rentifytools.entity.Address;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "RequestDTO class for User")
public class UserRequestDto {
    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    @Schema(description = "User firstname", example = "John")
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    @Schema(description = "User lastname", example = "Johnson")
    private String lastname;

    @NotBlank
    @Size(min = 5, max = 35)
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "john@example.com")
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*().])[A-Za-z\\d!@#$%^&*().]{5,15}$",
            message = "Password must be 5-15 characters long, contain at least one uppercase letter and one digit")
    @Schema(description = "User password", example = "john1234")
    private String password;

    @NotBlank
    @Size(min = 6, max = 14)
    @Pattern(regexp = "^\\+?[1-9]\\d{0,2}[- .]?\\(?\\d{1,4}\\)?[- .]?\\d{1,4}[- .]?\\d{1,9}|^\\d{10,15}$", message = "Invalid phone number")
    @Schema(description = "User phone number", example = "123-456-789")
    private String phone;

    @Schema(description = "User address. Connected with a separate table with OneToOne relationship")
    private AddressRequestDto address;
}
