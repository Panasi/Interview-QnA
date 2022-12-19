package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	CategoryDto toCategoryDto(Category category);
	List<CategoryDto> toCategoryDtos(List<Category> categories);
	@Mapping(target = "questions", ignore = true)
	Category toCategory(CategoryDto categoryDto);
}
