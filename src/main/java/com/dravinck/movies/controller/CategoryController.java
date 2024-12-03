package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.CategoryRequest;
import com.dravinck.movies.controller.response.CategoryResponse;
import com.dravinck.movies.entity.Category;
import com.dravinck.movies.mapper.CategoryMapper;
import com.dravinck.movies.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/category")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    @PostMapping
    public CategoryResponse saveCategory(@RequestBody CategoryRequest request) {
        Category newNategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.saveCategory(newNategory);
        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @GetMapping("/{id}")
    public CategoryResponse getByCategoryId(@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.findById(id);
        if (optCategory.isPresent()) {
            return CategoryMapper.toCategoryResponse(optCategory.get());
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteByCategoryId(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}

