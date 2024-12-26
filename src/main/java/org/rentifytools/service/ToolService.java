package org.rentifytools.service;

import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.dto.toolDto.ToolUserResponseDto;
import org.rentifytools.security.utils.SecurityUtils;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

public interface ToolService {

    List<ToolUserResponseDto> getAllTools();

    ToolUserResponseDto getToolById(Long toolId);

    List<ToolResponseDto> getToolsByTitle(String toolName);

    List<ToolResponseDto> getByTitleContaining(String toolName);

    List<ToolResponseDto> getToolsByCategory(Long categoryId);

    List<ToolResponseDto> getAllToolsByUser();

    ToolResponseDto addNewTool(ToolRequestDto tool);

    ToolResponseDto updateTool(Long toolId, ToolRequestDto dto);

    ToolResponseDto setToolStatus(Long toolId, ToolsAvailabilityStatus status);

    ToolResponseDto deleteTool(Long toolId);

//   ===================================
//    List<ToolResponseDto> getToolsByStatus(ToolsAvailabilityStatus status);
//   ===================================
//    List<ToolResponseDto> getAllToolsByUser(Long userId);

}