package com.panasi.interview_questions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.entity.Category;
import com.panasi.interview_questions.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	
	@GetMapping("/all")
	public List<Category> showAllCategories() {
		List<Category> allCategories = service.getAllCategories();
		return allCategories;
	}
	@GetMapping("/all/{id}")
	public Category showCategory(@PathVariable int id) {
		Category category = service.getCategoryById(id);
		return category;
	}
	
	@PostMapping("/all")
	public Category addNewCategory(@RequestBody Category category) {
		service.saveCategory(category);
		return category;
	}
	
	@PutMapping("/all")
	public Category updateCategory(@RequestBody Category category) {
		service.saveCategory(category);
		return category;
	}
	
	@DeleteMapping("/all/{id}")
	public String deleteCategory(@PathVariable int id) {
		service.deleteCategory(id);
		return "Category " + id + " was deleted!";
	}

}
