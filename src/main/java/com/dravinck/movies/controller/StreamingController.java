package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.CategoryRequest;
import com.dravinck.movies.controller.response.CategoryResponse;
import com.dravinck.movies.controller.response.StreamingResponse;
import com.dravinck.movies.entity.Category;
import com.dravinck.movies.entity.Streaming;
import com.dravinck.movies.mapper.CategoryMapper;
import com.dravinck.movies.mapper.StreamingMapper;
import com.dravinck.movies.service.CategoryService;
import com.dravinck.movies.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/streaming")
@RequiredArgsConstructor
public class StreamingController {
    private final StreamingService streamingService;



    @GetMapping
    public List<StreamingResponse> getAllStreaming() {
        List<Streaming> streamings = streamingService.findAll();
        return streamings.stream() //steam é uma sequencia de elementos que suporta operações sequenciais e paralelas
                .map(category -> StreamingMapper.toStreamingResponse(streamings)) // mapea cada objeto Category para um objeto CategoryResponse
                .toList();
    }

    @PostMapping
    public CategoryResponse saveCategory(@RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request); // transforma a requisiçao HTTP em um objeto Category
        Category savedCategory = categoryService.saveCategory(newCategory);
        return CategoryMapper.toCategoryResponse(savedCategory); // transforma o objeto Category em uma resposta HTTP
    }

    @GetMapping("/{id}")
    public CategoryResponse getByCategoryId(@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.findById(id);
        return optCategory.map(CategoryMapper::toCategoryResponse).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteByCategoryId(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
