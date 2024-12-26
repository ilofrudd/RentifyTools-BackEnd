package org.rentifytools.dto.userDto;

import lombok.*;
import org.checkerframework.checker.units.qual.N;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchRequestDto {
    private String lastname;
    private String email;
    private String phone;
}
