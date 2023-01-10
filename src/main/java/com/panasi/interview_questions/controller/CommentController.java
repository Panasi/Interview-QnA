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
import com.panasi.interview_questions.repository.dto.CommentDto;
import com.panasi.interview_questions.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private final CommentService service;
	
	
	@GetMapping("/answer/{id}")
	@Operation(summary = "Get all comments to the answer")
	public ResponseEntity<List<CommentDto>> showAllCommentsToAnswer(@PathVariable int id) {
		List<CommentDto> allCommentDtos = service.getAllCommentsToAnswer(id);
		return new ResponseEntity<>(allCommentDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get comment by id")
	public ResponseEntity<CommentDto> showCommentById(@PathVariable int id) {
		CommentDto commentDto = service.getCommentById(id);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Add a new comment")
	public ResponseEntity<CommentRequest> addNewComment(@RequestBody CommentRequest commentRequest) {
		service.createComment(commentRequest);
		return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Update comment")
	public ResponseEntity<CommentRequest> updateComment(@RequestBody CommentRequest commentRequest, @PathVariable int id) {
		service.updateComment(commentRequest, id);
		return new ResponseEntity<>(commentRequest, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Delete comment")
	public ResponseEntity<?> deleteComment(@PathVariable int id) {
		service.deleteComment(id);
		String message = "Comment " + id + " deleted";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
