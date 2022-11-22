package com.panasi.interview_questions.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService service;
	
	
	@GetMapping
	@Operation(summary = "Get all categories")
	public ResponseEntity<List<CategoryDto>> showAllCategories() {
		List<CategoryDto> allCategoryDtos = service.getAllCategories();
		if (allCategoryDtos == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allCategoryDtos, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	@Operation(summary = "Get a category by id")
	public ResponseEntity<CategoryDto> showCategory(@PathVariable int id) {
		CategoryDto categoryDto = service.getCategoryById(id);
		if (categoryDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Add a new category")
	public ResponseEntity<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto) {
		service.saveCategory(categoryDto);
		return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
	}
	
	@PutMapping
	@Operation(summary = "Update category")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
		service.saveCategory(categoryDto);
		return new ResponseEntity<>(categoryDto, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete category")
	public ResponseEntity<Integer> deleteCategory(@PathVariable int id) {
		CategoryDto categoryDto = service.getCategoryById(id);
		if (categoryDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.deleteCategory(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

}
