package com.panasi.interview_questions.service;

import java.util.List;

import com.panasi.interview_questions.entity.Category;

public interface CategoryService {
	
public List<Category> getAllCategories();
	
	public Category getCategoryById(int id);
	
	public void saveCategory(Category category);
	
	public void deleteCategory(int id);

}
