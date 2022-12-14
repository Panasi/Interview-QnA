package com.panasi.interview_questions.controller;

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

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/answers")
public class AnswerController {
	
	private final AnswerService service;
	
	
	@GetMapping
	@Operation(summary = "Get all answers")
	public ResponseEntity<List<AnswerDto>> showAllAnswers() {
		List<AnswerDto> allAnswerDtos = service.getAllAnswers();
		return new ResponseEntity<>(allAnswerDtos, HttpStatus.OK);
	}
	
	@GetMapping("/question/{id}")
	@Operation(summary = "Get all answers to the question")
	public ResponseEntity<List<AnswerDto>> showAllAnswersToQuestion(@PathVariable int id) {
		List<AnswerDto> allAnswerDtos = service.getAllAnswersToQuestion(id);
		return new ResponseEntity<>(allAnswerDtos, HttpStatus.OK);
	}
	
	@GetMapping("/public")
	@Operation(summary = "Get all public answers")
	public ResponseEntity<List<AnswerDto>> showAllPublicQuestions() {
		List<AnswerDto> allAnswerDtos = service.getAllPublicAnswers();
		return new ResponseEntity<>(allAnswerDtos, HttpStatus.OK);
	}
	
	@GetMapping("/private")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Get all private answers")
	public ResponseEntity<List<AnswerDto>> showAllPrivateQuestions() {
		List<AnswerDto> allAnswerDtos = service.getAllPrivateAnswers();
		return new ResponseEntity<>(allAnswerDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a answer by id")
	public ResponseEntity<AnswerDto> showAnswerById(@PathVariable int id) {
		AnswerDto answerDto = service.getAnswerById(id);
		return new ResponseEntity<>(answerDto, HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new answer")
	public ResponseEntity<AnswerRequest> addNewAnswer(@RequestBody AnswerRequest answerRequest) {
		service.createAnswer(answerRequest);
		return new ResponseEntity<>(answerRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Update answer")
	public ResponseEntity<AnswerRequest> updateAnswer(@RequestBody AnswerRequest answerRequest, @PathVariable int id) {
		service.updateAnswer(answerRequest, id);
		return new ResponseEntity<>(answerRequest, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Delete answer")
	public ResponseEntity<?> deleteAnswer(@PathVariable int id) {
		service.deleteAnswer(id);
		String message = "Answer " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
