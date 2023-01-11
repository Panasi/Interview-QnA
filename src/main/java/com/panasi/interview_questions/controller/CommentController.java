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

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private final CommentService service;
	
	
	@GetMapping("/toquestion/{id}")
	@Operation(summary = "Get all comments to the question")
	public ResponseEntity<List<QuestionCommentDto>> showAllCommentsToQuestion(@PathVariable int id) {
		List<QuestionCommentDto> allCommentDtos = service.getAllCommentsToQuestion(id);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/toanswer/{id}")
	@Operation(summary = "Get all comments to the answer")
	public ResponseEntity<List<AnswerCommentDto>> showAllCommentsToAnswer(@PathVariable int id) {
		List<AnswerCommentDto> allCommentDtos = service.getAllCommentsToAnswer(id);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/question/{id}")
	@Operation(summary = "Get question comment by id")
	public ResponseEntity<QuestionCommentDto> showQuestionCommentById(@PathVariable int id) {
		QuestionCommentDto commentDto = service.getQuestionCommentById(id);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@GetMapping("/answer/{id}")
	@Operation(summary = "Get answer comment by id")
	public ResponseEntity<AnswerCommentDto> showAnswerCommentById(@PathVariable int id) {
		AnswerCommentDto commentDto = service.getAnswerCommentById(id);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PostMapping("/question")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new question comment")
	public ResponseEntity<CommentRequest> addNewQuestionComment(@RequestBody CommentRequest commentRequest) {
		service.createQuestionComment(commentRequest);
		return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
	}
	
	@PostMapping("/answer")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new answer comment")
	public ResponseEntity<CommentRequest> addNewAnswerComment(@RequestBody CommentRequest commentRequest) {
		service.createAnswerComment(commentRequest);
		return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/question/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Update question comment")
	public ResponseEntity<CommentRequest> updateQuestionComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		service.updateQuestionComment(commentRequest, id);
		return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/answer/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Update answer comment")
	public ResponseEntity<CommentRequest> updateAnswerComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		service.updateAnswerComment(commentRequest, id);
		return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/question/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Delete question comment")
	public ResponseEntity<?> deleteQuestionComment(@PathVariable int id) {
		service.deleteQuestionComment(id);
		String message = "Comment " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}
	
	@DeleteMapping("/answer/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Delete answer comment")
	public ResponseEntity<?> deleteAnswerComment(@PathVariable int id) {
		service.deleteAnswerComment(id);
		String message = "Comment " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
