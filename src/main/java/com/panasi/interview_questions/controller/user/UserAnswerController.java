package com.panasi.interview_questions.controller.user;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.service.user.UserAnswerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/answers")
public class UserAnswerController {
	
	private final UserAnswerService service;
	
	
	@GetMapping("/user/{authorId}")
	@Operation(summary = "Get user answers")
	public ResponseEntity<?> showUserAnswers(@PathVariable int authorId, @RequestParam(defaultValue = "all") String access) {
		List<AnswerDto> allAnswerDtos = service.getUserAnswers(authorId, access);
		if (allAnswerDtos == null) {
			String message = "You don't have access to user private answers";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(allAnswerDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a full answer with comments by id")
	public ResponseEntity<?> showAnswerById(@PathVariable int id) {
		FullAnswerDto answerDto = service.getAnswerById(id);
		if (answerDto == null) {
			String message = "Answer " + id + " is private";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(answerDto, HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add a new answer")
	public ResponseEntity<AnswerRequest> addNewAnswer(@RequestBody AnswerRequest answerRequest) {
		service.createAnswer(answerRequest);
		return new ResponseEntity<>(answerRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Update answer")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerRequest answerRequest, @PathVariable int id) {
		boolean result = service.updateAnswer(answerRequest, id);
		if (result) {
			return new ResponseEntity<>(answerRequest, HttpStatus.ACCEPTED);
		}
		String message = "You can't update other users answers";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}

}
