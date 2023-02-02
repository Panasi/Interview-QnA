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

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.service.admin.AdminCommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/comments")
public class AdminCommentController {
	
	private final AdminCommentService service;
	
	
	@GetMapping("/question/{questionId}/all")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all comments to the question")
	public ResponseEntity<List<QuestionCommentDto>> showAllCommentsToQuestion(@PathVariable int questionId) {
		List<QuestionCommentDto> allCommentDtos = service.getAllCommentsToQuestion(questionId);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/answer/{answerId}/all")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all comments to the answer")
	public ResponseEntity<List<AnswerCommentDto>> showAllCommentsToAnswer(@PathVariable int answerId) {
		List<AnswerCommentDto> allCommentDtos = service.getAllCommentsToAnswer(answerId);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/questions")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all user comments to the question")
	public ResponseEntity<List<QuestionCommentDto>> showAllUserCommentsToQuestions(@PathVariable int userId) {
		List<QuestionCommentDto> allCommentDtos = service.getAllUserCommentsToQuestions(userId);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/answers")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all user comments to the answer")
	public ResponseEntity<List<AnswerCommentDto>> showAllUserCommentsToAnswers(@PathVariable int userId) {
		List<AnswerCommentDto> allCommentDtos = service.getAllUserCommentsToAnswers(userId);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/questions/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get question comment by id")
	public ResponseEntity<QuestionCommentDto> showQuestionCommentById(@PathVariable int id) {
		QuestionCommentDto commentDto = service.getQuestionCommentById(id);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@GetMapping("/answers/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get answer comment by id")
	public ResponseEntity<AnswerCommentDto> showAnswerCommentById(@PathVariable int id) {
		AnswerCommentDto commentDto = service.getAnswerCommentById(id);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PostMapping("/question/{questionId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add a new question comment")
	public ResponseEntity<CommentRequest> addNewQuestionComment(@RequestBody CommentRequest commentRequest, @PathVariable int questionId) {
		service.createQuestionComment(commentRequest, questionId);
		return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
	}
	
	@PostMapping("/answer/{answerId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new answer comment")
	public ResponseEntity<CommentRequest> addNewAnswerComment(@RequestBody CommentRequest commentRequest, @PathVariable int answerId) {
		service.createAnswerComment(commentRequest, answerId);
		return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/questions/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update question comment")
	public ResponseEntity<CommentRequest> updateQuestionComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		service.updateQuestionComment(commentRequest, id);
		return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/answers/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update answer comment")
	public ResponseEntity<CommentRequest> updateAnswerComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		service.updateAnswerComment(commentRequest, id);
		return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/questions/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete question comment")
	public ResponseEntity<MessageResponse> deleteQuestionComment(@PathVariable int id) {
		service.deleteQuestionComment(id);
		String message = "Comment " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}
	
	@DeleteMapping("/answers/comment/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete answer comment")
	public ResponseEntity<MessageResponse> deleteAnswerComment(@PathVariable int id) {
		service.deleteAnswerComment(id);
		String message = "Comment " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
