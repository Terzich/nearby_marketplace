package com.example.nearby.controller;

import com.example.nearby.datamodel.domain.Category;
import com.example.nearby.datamodel.domain.Product;
import com.example.nearby.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        Optional<Category> categoryOptional = categoryService.getById(categoryId);
        return categoryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category createdCategory = categoryService.create(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(createdCategory.getCategoryId()).toUri();
        return ResponseEntity.created(uri).body(createdCategory);
    }

    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
        boolean isCategoryUpdated = categoryService.updateCategory(category, categoryId);
        return  isCategoryUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        if(!categoryService.doesEntityExist(categoryId)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
