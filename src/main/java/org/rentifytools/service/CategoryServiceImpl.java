package org.rentifytools.service;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Category;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category createCategory(String title, byte[] image) {
        Category category = new Category();
        category.setTitle(title);
        category.setImage(image);
        return repository.save(category);
    }

    @Override
    public Category updateCategory(Long id, String title, byte[] image) {
        Category category = getCategoryById(id);
        category.setTitle(title);
        category.setImage(image);
        return repository.save(category);
    }

    @Override
    public Category deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (category != null) {
            repository.delete(category);
        }
        return category;
    }
}
