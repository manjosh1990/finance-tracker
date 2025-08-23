package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.categories.Category;
import com.manjosh.labs.backend.domain.categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody final Category category) {
        final Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoriesForCurrentProfile());
    }

    @GetMapping("/{type}")
    public ResponseEntity<Iterable<Category>> getCategoriesByType(@PathVariable final String type) {
        return ResponseEntity.ok(categoryService.getCategoriesByTypeForCurrentProfile(type));
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody final Category category) {
        final Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }
}
