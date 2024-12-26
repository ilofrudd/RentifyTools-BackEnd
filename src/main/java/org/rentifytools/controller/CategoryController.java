package org.rentifytools.controller;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Category;
import org.rentifytools.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestParam String title,
                                                   @RequestParam MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCategory(title, imageData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestParam String title,
                                                   @RequestParam MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();
        return ResponseEntity.ok(service.updateCategory(id, title, imageData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteCategory(id));
    }
}
