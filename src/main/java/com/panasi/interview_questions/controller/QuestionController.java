package com.panasi.interview_questions.controller;

import java.util.ArrayList;
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

import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	private final QuestionService service;
	

	@GetMapping
	@Operation(summary = "Get all questions")
	public ResponseEntity<List<QuestionDto>> showAllQuestions() {
		List<QuestionDto> allQuestionDtos = service.getAllQuestions();
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	@Operation(summary = "Get questions from certain category")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromCategory(@PathVariable int id) {
		List<QuestionDto> allQuestionDtos = service.getQuestionsFromCategory(id);
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/subcategory/{id}")
	@Operation(summary = "Get questions from certain category and all its subcategories")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromSubcategories(@PathVariable int id) {
		List<QuestionDto> result = new ArrayList<>();
		List<QuestionDto> allSubQuestionDtos = service.getQuestionsFromSubcategories(id, result);
		return new ResponseEntity<>(allSubQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a question by id")
	public ResponseEntity<QuestionDto> showQuestion(@PathVariable int id) {
		QuestionDto questionDto = service.getQuestionById(id);
		return new ResponseEntity<>(questionDto, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Add a new question")
	public ResponseEntity<QuestionDto> addNewQuestion(@RequestBody QuestionDto questionDto) {
		service.createQuestion(questionDto);
		return new ResponseEntity<>(questionDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update question")
	public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto, @PathVariable int id) {
		service.updateQuestion(questionDto, id);
		return new ResponseEntity<>(questionDto, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete question")
	public ResponseEntity<?> deleteQuestion(@PathVariable int id) {
			service.deleteQuestion(id);
			String message = "Question " + id + " is deleted";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
