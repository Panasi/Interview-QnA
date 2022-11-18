package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.entity.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Category getCategoryById(int id) {
		Optional<Category> optional = categoryRepository.findById(id);
		Category category = null;
		if (optional.isPresent()) {
			category = optional.get();
		}
		return category;
	}
	
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public void deleteCategory(int id) {
		categoryRepository.deleteById(id);
	}

}
