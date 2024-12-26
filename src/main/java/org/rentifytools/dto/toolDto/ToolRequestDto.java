package org.rentifytools.dto.toolDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "RequestDto class for Tool")
public class ToolRequestDto {

    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private List<String> imageUrls;
    private Double price;
    private List<Long> categoryIds;
}