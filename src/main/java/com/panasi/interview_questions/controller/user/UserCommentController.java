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
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.service.user.UserCommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class UserCommentController {
	
	private final UserCommentService service;
	
	
	@GetMapping("/question/{questionId}/all")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Get all comments to the question")
	public ResponseEntity<?> showAllCommentsToQuestion(@PathVariable int questionId) {
		List<QuestionCommentDto> allCommentDtos = service.getAllCommentsToQuestion(questionId);
		if (allCommentDtos == null) {
			String message = "You can't get comments to another user's private question";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/answer/{answerId}/all")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Get all comments to the answer")
	public ResponseEntity<?> showAllCommentsToAnswer(@PathVariable int answerId) {
		List<AnswerCommentDto> allCommentDtos = service.getAllCommentsToAnswer(answerId);
		if (allCommentDtos == null) {
			String message = "You can't get comments to another user's private answer";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/questions/comment/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Get question comment by id")
	public ResponseEntity<?> showQuestionCommentById(@PathVariable int id) {
		QuestionCommentDto commentDto = service.getQuestionCommentById(id);
		if (commentDto == null) {
			String message = "You can't get comment to another's user's private question";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@GetMapping("/answers/comment/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Get answer comment by id")
	public ResponseEntity<?> showAnswerCommentById(@PathVariable int id) {
		AnswerCommentDto commentDto = service.getAnswerCommentById(id);
		if (commentDto == null) {
			String message = "You can't get comment to another's user's private answer";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PostMapping("/question/{questionId}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add a new question comment")
	public ResponseEntity<?> addNewQuestionComment(@RequestBody CommentRequest commentRequest, @PathVariable int questionId) {
		boolean result = service.createQuestionComment(commentRequest, questionId);
		if (result) {
			return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
		}
		String message = "You can't comment user's private question";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/answer/{answerId}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add a new answer comment")
	public ResponseEntity<?> addNewAnswerComment(@RequestBody CommentRequest commentRequest, @PathVariable int answerId) {
		boolean result = service.createAnswerComment(commentRequest, answerId);
		if (result) {
			return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
		}
		String message = "You can't comment user's private answer";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/questions/comment/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Update question comment")
	public ResponseEntity<?> updateQuestionComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		boolean result = service.updateQuestionComment(commentRequest, id);
		if (result) {
			return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
		}
		String message = "You can't update another user's comment";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/answers/comment/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Update answer comment")
	public ResponseEntity<?> updateAnswerComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		boolean result = service.updateAnswerComment(commentRequest, id);
		if (result) {
			return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
		}
		String message = "You can't update another user's comment";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}

}
