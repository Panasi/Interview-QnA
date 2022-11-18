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
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.service.CategoryService;
import com.panasi.interview_questions.service.mappers.CategoryMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService service;
	private final CategoryMapper mapper;
	
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> showAllCategories() {
		List<CategoryDto> allCategoryDtos = mapper.toCategoryDtos(service.getAllCategories());
		if (allCategoryDtos == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allCategoryDtos, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> showCategory(@PathVariable int id) {
		CategoryDto categoryDto = mapper.toCategoryDto(service.getCategoryById(id));
		if (categoryDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto) {
		Category category = mapper.toCategory(categoryDto);
		service.saveCategory(category);
		return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
		Category category = mapper.toCategory(categoryDto);
		service.saveCategory(category);
		return new ResponseEntity<>(categoryDto, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteCategory(@PathVariable int id) {
		CategoryDto category = mapper.toCategoryDto(service.getCategoryById(id));
		if (category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
		service.deleteCategory(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}

}
