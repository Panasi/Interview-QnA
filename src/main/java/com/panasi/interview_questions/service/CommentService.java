package com.panasi.interview_questions.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.repository.CommentRepository;
import com.panasi.interview_questions.repository.dto.CommentDto;
import com.panasi.interview_questions.repository.entity.Comment;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final CommentMapper mapper;
	
	
	// Return all comments to answer
	public List<CommentDto> getAllCommentsToAnswer(int answerId) {
		List<CommentDto> allCommentDtos = mapper.toCommentDtos(commentRepository.findAllByAnswerId(answerId));
		return allCommentDtos;
	}
	
	// Return comment by id
	public CommentDto getCommentById(int commentId) {
		Comment comment = commentRepository.findById(commentId).get();
		CommentDto commentDto = mapper.toCommentDto(comment);
		return commentDto;
		
	}
	
	// Add a new comment
	public void createComment(@Valid CommentRequest commentRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorName = userDetails.getUsername();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		CommentDto commentDto = new CommentDto();
		commentDto.setContent(commentRequest.getContent());
		commentDto.setRate(commentRequest.getRate());
		commentDto.setAnswerId(commentRequest.getAnswerId());
		commentDto.setAuthorName(authorName);
		commentDto.setAuthorId(authorId);
		commentDto.setDate(dateTime);
		Comment comment = mapper.toComment(commentDto);
		commentRepository.save(comment);
	}
	
	// Update certain comment
	public void updateComment(@Valid CommentRequest commentRequest, int commentId) {
		Comment comment = commentRepository.findById(commentId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setAuthorName(comment.getAuthorName());
		commentDto.setAuthorId(comment.getAuthorId());
		commentDto.setDate(dateTime);
		commentDto.setAnswerId(comment.getAnswerId());
		if (Objects.isNull(commentRequest.getContent())) {
			commentDto.setContent(comment.getContent());
		} else {
			commentDto.setContent(commentRequest.getContent());
		}
		if (Objects.isNull(commentRequest.getRate())) {
			commentDto.setRate(comment.getRate());
		} else {
			commentDto.setRate(commentRequest.getRate());
		}
		Comment updatedComment = mapper.toComment(commentDto);
		commentRepository.save(updatedComment);
	}
	
	// Delete certain comment
	public void deleteComment(int commentId) {
		commentRepository.deleteById(commentId);
	}

}
