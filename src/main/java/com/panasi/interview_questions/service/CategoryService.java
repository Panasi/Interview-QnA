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
	
	
	// Return all categories
	public List<CategoryDto> getAllCategories() {
		List<CategoryDto> allCategoryDtos = mapper.toCategoryDtos(categoryRepository.findAll());
		return allCategoryDtos;
	}
	
	// Return subcategories of category
	public List<CategoryDto> getAllSubcategories(int parentId) {
		List<CategoryDto> allSubcategoryDtos = mapper.toCategoryDtos(categoryRepository.findAllByParentId(parentId));
		return allSubcategoryDtos;
	}
	
	// Return category by id
	public CategoryDto getCategoryById(int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		CategoryDto	categoryDto = mapper.toCategoryDto(category);
		return categoryDto;
	}
	
	// Add a new category
	public void createCategory(CategoryDto categoryDto) {
		Category category = mapper.toCategory(categoryDto);
		categoryRepository.save(category);
	}
	
	// Update certain category
	public void updateCategory(CategoryDto categoryDto, int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		categoryDto.setId(categoryId);
		if (Objects.isNull(categoryDto.getParentId())) {
			categoryDto.setParentId(category.getParentId());
		}
		if (Objects.isNull(categoryDto.getName())) {
			categoryDto.setName(category.getName());
		}
		Category updatedCategory = mapper.toCategory(categoryDto);
		categoryRepository.save(updatedCategory);
	}
	
	// Delete certain category
	public void deleteCategory(int categoryId) {
		List<Category> allSubcategoryDtos = categoryRepository.findAllByParentId(categoryId);
		if (allSubcategoryDtos.isEmpty()) {
			categoryRepository.deleteById(categoryId);
		} else {
			allSubcategoryDtos.forEach(category -> {
				deleteCategory(category.getId());
			});
			categoryRepository.deleteById(categoryId);
		}
	}

}
