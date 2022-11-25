package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;

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
	
	public List<CategoryDto> getAllSubcategories(int parentId) {
		List<CategoryDto> allSubcategoryDtos = mapper.toCategoryDtos(categoryRepository.findAllByParentId(parentId));
		return allSubcategoryDtos;
	}
	
	public CategoryDto getCategoryById(int id) {
		Category category = categoryRepository.findById(id).get();
		CategoryDto	categoryDto = mapper.toCategoryDto(category);
		return categoryDto;
	}
	
	public void createCategory(CategoryDto categoryDto) {
		Category category = mapper.toCategory(categoryDto);
		categoryRepository.save(category);
	}
	
	public void updateCategory(CategoryDto categoryDto, int id) {
		Category category = categoryRepository.findById(id).get();
		categoryDto.setId(id);
		if (Objects.isNull(categoryDto.getParentId())) {
			categoryDto.setParentId(category.getParentId());
		}
		if (Objects.isNull(categoryDto.getName())) {
			categoryDto.setName(category.getName());
		}
		Category updatedCategory = mapper.toCategory(categoryDto);
		categoryRepository.save(updatedCategory);
	}
	
	public void deleteCategory(int id) {
		List<Category> allSubcategoryDtos = categoryRepository.findAllByParentId(id);
		allSubcategoryDtos.forEach(category -> {
			categoryRepository.deleteById(category.getId());
		});
		categoryRepository.deleteById(id);
	}

}
