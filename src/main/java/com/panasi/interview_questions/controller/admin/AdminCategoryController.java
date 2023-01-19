package com.panasi.interview_questions.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.CategoryRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	private final CategoryService service;
	
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all categories")
	public ResponseEntity<List<CategoryDto>> showAllCategories() {
		List<CategoryDto> allCategoryDtos = service.getAllCategories();
		return new ResponseEntity<>(allCategoryDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/subcategories")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all subcategories")
	public ResponseEntity<List<CategoryDto>> showSubcategories(@PathVariable int id) {
		List<CategoryDto> allCategoryDtos = service.getAllSubcategories(id);
		return new ResponseEntity<>(allCategoryDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get a category by id")
	public ResponseEntity<CategoryDto> showCategoryById(@PathVariable int id) {
		CategoryDto categoryDto = service.getCategoryById(id);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add a new category")
	public ResponseEntity<CategoryRequest> addNewCategory(@RequestBody CategoryRequest categoryRequest) {
		service.createCategory(categoryRequest);
		return new ResponseEntity<>(categoryRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update category")
	public ResponseEntity<CategoryRequest> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable int id) {
		service.updateCategory(categoryRequest, id);
		return new ResponseEntity<>(categoryRequest, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete category and all subcategories")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		service.deleteCategory(id);
		String message = "Category " + id + " and all its subcategories are deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
