package com.panasi.interview_questions.service.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.entity.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper {
	
	@Override
	public CategoryDto toCategoryDto(Category category) {
		if (category == null) {
			return null;
		}
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		return categoryDto;
	}
	
	@Override
	public List<CategoryDto> toCategoryDtos(List<Category> categories) {
		if (categories.isEmpty()) {
			return null;
		}
		List<CategoryDto> categoriesDto = new ArrayList<>();
		categories.forEach(category -> {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setId(category.getId());
			categoryDto.setName(category.getName());
			categoriesDto.add(categoryDto);
		});
		return categoriesDto;
	}
	
	@Override
	public Category toCategory(CategoryDto categoryDto) {
		if (categoryDto == null) {
			return null;
		}
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		return category;
	}

}
