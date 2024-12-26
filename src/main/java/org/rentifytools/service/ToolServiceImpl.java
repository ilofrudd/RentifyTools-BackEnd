package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Category;
import org.rentifytools.dto.toolDto.ToolUserResponseDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.entity.ToolImage;
import org.rentifytools.entity.User;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.CategoryRepository;
import org.rentifytools.repository.ToolImageRepository;
import org.rentifytools.repository.ToolRepository;
import org.rentifytools.repository.UserRepository;
import org.rentifytools.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolImageRepository toolImageRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CloudStorageService storageService;
    private final ModelMapper mapper;

    @Override
    public List<ToolUserResponseDto> getAllTools() {
        return toolRepository.findAll().stream()
                .map(tool -> mapper.map(tool, ToolUserResponseDto.class)).toList();
    }

    public Tool findToolById(Long toolId) {
        String exceptionMessage = "Tool with ID %d not found";
        return toolRepository.findById(toolId)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, toolId)));
    }

    public User getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        String exceptionMessage = "User with ID %d not found";
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, userId)));
    }

    @Override
    public ToolUserResponseDto getToolById(Long toolId) {
        return mapper.map(findToolById(toolId), ToolUserResponseDto.class);
    }

    @Override
    public List<ToolResponseDto> getToolsByTitle(String toolName) {
        return toolRepository.findByTitle(toolName).stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    public List<ToolResponseDto> getByTitleContaining(String toolName) {
        return toolRepository.findByTitleContaining(toolName).stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    public List<ToolResponseDto> getToolsByCategory(Long categoryId) {
        return toolRepository.findByCategoriesId(categoryId).stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    public List<ToolResponseDto> getAllToolsByUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<Tool> toolsByUserId = toolRepository.findAllByUserId(userId);
        return toolsByUserId.stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public ToolResponseDto addNewTool(ToolRequestDto dto) {
        Tool tool = mapper.map(dto, Tool.class);
        User user = getCurrentUser();
        tool.setUser(user);

        if (dto.getStatus() == null) {
            tool.setStatus(ToolsAvailabilityStatus.AVAILABLE);
        }

        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            tool.setCategories(categories);
        }
        Tool savedTool = toolRepository.save(tool);

        if (dto.getImageUrls() != null) {
            System.out.println("Creating ToolImages from DTO imageUrls: " + dto.getImageUrls());
            List<ToolImage> images = dto.getImageUrls().stream()
                    .map(url -> new ToolImage(null, savedTool, url))
                    .toList();
            toolImageRepository.saveAll(images);
            savedTool.setImageUrls(images);
        }
        return mapper.map(savedTool, ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto updateTool(Long toolId, ToolRequestDto dto) {
        Tool foundTool = findToolById(toolId);
        mapper.map(dto, foundTool);

        if (dto.getCategoryIds() != null) {
            List<Category> updatedCategories = categoryRepository.findAllById(dto.getCategoryIds());
            foundTool.setCategories(updatedCategories);
        }

        if (dto.getImageUrls() != null) {
            List<String> newImageUrls = dto.getImageUrls();
            List<ToolImage> currentImages = foundTool.getImageUrls();

            List<ToolImage> imagesToRemove = currentImages.stream()
                    .filter(image -> !newImageUrls.contains(image.getImageUrl()))
                    .toList();
            imagesToRemove.forEach(image -> storageService.deleteFileByUrl(image.getImageUrl()));
            currentImages.removeAll(imagesToRemove);

            List<ToolImage> imagesToAdd = newImageUrls.stream()
                    .filter(url -> currentImages.stream().noneMatch(image -> image.getImageUrl().equals(url)))
                    .map(url -> new ToolImage(null, foundTool, url))
                    .toList();

            currentImages.addAll(imagesToAdd);
        }
        return mapper.map(toolRepository.save(foundTool), ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto setToolStatus(Long toolId, ToolsAvailabilityStatus status) {
        Tool tool = findToolById(toolId);
        tool.setStatus(status);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto deleteTool(Long toolId) {
        Tool tool = findToolById(toolId);

        tool.getImageUrls().forEach(image -> storageService.deleteFileByUrl(image.getImageUrl()));

        toolRepository.deleteById(toolId);
        return mapper.map(tool, ToolResponseDto.class);
    }
}