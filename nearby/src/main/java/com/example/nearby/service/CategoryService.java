package com.example.nearby.service;

import com.example.nearby.datamodel.domain.Category;
import com.example.nearby.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends AbstractBaseService<Category, CategoryRepository> {

    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    public boolean updateCategory(Category category, Long categoryId) {
        CategoryRepository repository = getRepository();
        Optional<Category> categoryOptional = repository.findById(categoryId);
        if(categoryOptional.isPresent()) {
            Category persistedCategory = categoryOptional.get();
            persistedCategory.setName(category.getName());
            repository.save(persistedCategory);
            return true;
        } else {
            return false;
        }
    }
}
