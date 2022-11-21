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

import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	private final QuestionService service;
	
	
	@GetMapping
	public ResponseEntity<List<QuestionDto>> showAllQuestions() {
		List<QuestionDto> allQuestionDtos = service.getAllQuestions();
		if (allQuestionDtos == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<List<QuestionDto>> showAllQuestionsByCategory(@PathVariable int id) {
		List<QuestionDto> allQuestionDtos = service.getAllQuestionsByCategory(id);
		if (allQuestionDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<QuestionDto> showQuestion(@PathVariable int id) {
		QuestionDto questionDto = service.getQuestionById(id);
		if (questionDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(questionDto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<QuestionDto> addNewQuestion(@RequestBody QuestionDto questionDto) {
		service.saveQuestion(questionDto);
		return new ResponseEntity<>(questionDto, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto) {
		service.saveQuestion(questionDto);
		return new ResponseEntity<>(questionDto, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteQuestion(@PathVariable int id) {
		QuestionDto question = service.getQuestionById(id);
		if (question == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.deleteQuestion(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

}
