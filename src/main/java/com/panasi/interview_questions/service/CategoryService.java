package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.CategoryRequest;
import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.service.mappers.CategoryMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final QuestionMapper questionMapper;
	
	
	// Return all categories
	public List<CategoryDto> getAllCategories() {
		List<CategoryDto> allCategoryDtos = categoryMapper.toCategoryDtos(categoryRepository.findAll());
		return allCategoryDtos;
	}
	
	// Return subcategories of category
	public List<CategoryDto> getAllSubcategories(int parentId) {
		List<CategoryDto> allSubcategoryDtos = categoryMapper.toCategoryDtos(categoryRepository.findAllByParentId(parentId));
		return allSubcategoryDtos;
	}
	
	// Return category by id
	public CategoryDto getCategoryById(int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		CategoryDto	categoryDto = categoryMapper.toCategoryDto(category);
		return categoryDto;
	}
	
	// Add a new category
	public void createCategory(CategoryRequest categoryRequest) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(categoryRequest.getName());
		categoryDto.setParentId(categoryRequest.getParentId());
		Category category = categoryMapper.toCategory(categoryDto);
		categoryRepository.save(category);
	}
	
	// Update certain category
	public void updateCategory(CategoryRequest categoryRequest, int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(categoryId);
		if (Objects.isNull(categoryRequest.getName())) {
			categoryDto.setName(category.getName());
		} else {
			categoryDto.setName(categoryRequest.getName());
		}
		if (Objects.isNull(categoryRequest.getParentId())) {
			categoryDto.setParentId(category.getParentId());
		} else {
			categoryDto.setParentId(categoryRequest.getParentId());
		}
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(category.getQuestions());
		categoryDto.setQuestions(questionDtos);
		Category updatedCategory = categoryMapper.toCategory(categoryDto);
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
