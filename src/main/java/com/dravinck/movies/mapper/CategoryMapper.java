package com.dravinck.movies.mapper;

import com.dravinck.movies.controller.request.CategoryRequest;
import com.dravinck.movies.controller.response.CategoryResponse;
import com.dravinck.movies.entity.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static Category toCategory(CategoryRequest category) {
        return Category
                .builder()
                .name(category.name())
                .build();

    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
