package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	CategoryDto toCategoryDto(Category category);
	List<CategoryDto> toCategoryDtos(List<Category> categories);
	Category toCategory(CategoryDto categoryDto);
}
