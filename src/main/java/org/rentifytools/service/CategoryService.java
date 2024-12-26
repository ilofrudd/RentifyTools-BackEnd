package org.rentifytools.service;

import org.rentifytools.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createCategory(String title, byte[] image);
    Category updateCategory(Long id, String title, byte[] image);
    Category deleteCategory(Long id);
}
