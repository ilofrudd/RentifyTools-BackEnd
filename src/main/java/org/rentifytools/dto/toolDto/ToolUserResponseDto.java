package org.rentifytools.dto.toolDto;

import lombok.*;
import org.rentifytools.dto.userDto.UserSimpleResponseDto;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ToolUserResponseDto {
    private Long id;
    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private List<String> imageUrls;
    private Double price;
    private UserSimpleResponseDto user;
}