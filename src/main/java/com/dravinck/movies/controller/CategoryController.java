package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.CategoryRequest;
import com.dravinck.movies.controller.response.CategoryResponse;
import com.dravinck.movies.entity.Category;
import com.dravinck.movies.mapper.CategoryMapper;
import com.dravinck.movies.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/category")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll()
                .stream() //steam é uma sequencia de elementos que suporta operações sequenciais e paralelas
                .map(category -> CategoryMapper.toCategoryResponse(category)) // mapea cada objeto Category para um objeto CategoryResponse
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest request) {
        Category savedCategory = categoryService.save(CategoryMapper.toCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategory));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getByCategoryId(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(Category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(Category)))
                .orElse(ResponseEntity.notFound().build());
   }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCategoryId(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

