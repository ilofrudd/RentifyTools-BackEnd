package org.rentifytools.dto.toolDto;

import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolResponseDto {
    private Long id;
    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private List<String> imageUrls;
    private Double price;
}