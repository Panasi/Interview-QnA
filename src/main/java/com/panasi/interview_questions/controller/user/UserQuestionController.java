package com.panasi.interview_questions.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.service.user.UserQuestionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class UserQuestionController {
	
	private final UserQuestionService service;
	
	
	@GetMapping("/category/{categoryId}")
	@Operation(summary = "Get questions from certain category")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromCategory(@PathVariable int categoryId) {
		List<QuestionDto> allQuestionDtos = service.getQuestionsFromCategory(categoryId);
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/subcategory/{categoryId}")
	@Operation(summary = "Get questions from certain category and all its subcategories")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromSubcategories(@PathVariable int categoryId) {
		List<QuestionDto> result = new ArrayList<>();
		List<QuestionDto> allSubQuestionDtos = service.getQuestionsFromSubcategories(categoryId, result);
		return new ResponseEntity<>(allSubQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/public")
	@Operation(summary = "Get all public questions")
	public ResponseEntity<List<QuestionDto>> showAllPublicQuestions() {
		List<QuestionDto> allQuestionDtos = service.getAllPublicQuestions();
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/user/{authorId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Get all private questions")
	public ResponseEntity<List<QuestionDto>> showAllPrivateQuestions(@PathVariable int authorId) {
		List<QuestionDto> allQuestionDtos = service.getAllUserQuestions(authorId);
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a question by id")
	public ResponseEntity<QuestionDto> showQuestion(@PathVariable int id) {
		QuestionDto questionDto = service.getQuestionById(id);
		return new ResponseEntity<>(questionDto, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new question")
	public ResponseEntity<QuestionRequest> addNewQuestion(@RequestBody QuestionRequest questionRequest) {
		service.createQuestion(questionRequest);
		return new ResponseEntity<>(questionRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Update question")
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionRequest questionRequest, @PathVariable int id) {
		boolean result = service.updateQuestion(questionRequest, id);
		if (result) {
			return new ResponseEntity<>(questionRequest, HttpStatus.ACCEPTED);
		}
		String message = "You can't update other users questions";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}

}
