package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.service.mappers.CategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper mapper;
	
	
	public List<CategoryDto> getAllCategories() {
		List<CategoryDto> allCategoryDtos = mapper.toCategoryDtos(categoryRepository.findAll());
		return allCategoryDtos;
	}
	
	public CategoryDto getCategoryById(int id) {
		Optional<Category> optional = categoryRepository.findById(id);
		CategoryDto categoryDto = null;
		if (optional.isPresent()) {
			categoryDto = mapper.toCategoryDto(optional.get());
		}
		return categoryDto;
	}
	
	public void saveCategory(CategoryDto categoryDto) {
		Category category = mapper.toCategory(categoryDto);
		categoryRepository.save(category);
	}
	
	public void deleteCategory(int id) {
		categoryRepository.deleteById(id);
	}

}