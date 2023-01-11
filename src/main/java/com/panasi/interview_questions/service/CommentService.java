package com.panasi.interview_questions.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.QuestionCommentRepository;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.repository.entity.QuestionComment;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerCommentMapper;
import com.panasi.interview_questions.service.mappers.QuestionCommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {
	
	private final AnswerCommentRepository answerCommentRepository;
	private final QuestionCommentRepository questionCommentRepository;
	private final AnswerCommentMapper answerCommentMapper;
	private final QuestionCommentMapper questionCommentMapper;
	
	
	// Return all comments to question
	public List<QuestionCommentDto> getAllCommentsToQuestion(int questionId) {
		List<QuestionComment> allComments = questionCommentRepository.findAllByQuestionId(questionId);
		List<QuestionCommentDto> allCommentDtos = questionCommentMapper.toCommentDtos(allComments);
		return allCommentDtos;
	}
	
	// Return all comments to answer
	public List<AnswerCommentDto> getAllCommentsToAnswer(int answerId) {
		List<AnswerComment> allComments = answerCommentRepository.findAllByAnswerId(answerId);
		List<AnswerCommentDto> allCommentDtos = answerCommentMapper.toCommentDtos(allComments);
		return allCommentDtos;
	}
	
	// Return question comment by id
	public QuestionCommentDto getQuestionCommentById(int commentId) {
		QuestionComment comment = questionCommentRepository.findById(commentId).get();
		QuestionCommentDto commentDto = questionCommentMapper.toCommentDto(comment);
		return commentDto;
			
	}
	
	// Return answer comment by id
	public AnswerCommentDto getAnswerCommentById(int commentId) {
		AnswerComment comment = answerCommentRepository.findById(commentId).get();
		AnswerCommentDto commentDto = answerCommentMapper.toCommentDto(comment);
		return commentDto;
		
	}
	
	// Add a new comment to question
	public void createQuestionComment(@Valid CommentRequest commentRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		QuestionCommentDto commentDto = new QuestionCommentDto();
		commentDto.setContent(commentRequest.getContent());
		commentDto.setRate(commentRequest.getRate());
		commentDto.setQuestionId(commentRequest.getParentId());
		commentDto.setAuthorId(authorId);
		commentDto.setDate(dateTime);
		QuestionComment comment = questionCommentMapper.toComment(commentDto);
		questionCommentRepository.save(comment);
	}
	
	// Add a new comment to answer
	public void createAnswerComment(@Valid CommentRequest commentRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerCommentDto commentDto = new AnswerCommentDto();
		commentDto.setContent(commentRequest.getContent());
		commentDto.setRate(commentRequest.getRate());
		commentDto.setAnswerId(commentRequest.getParentId());
		commentDto.setAuthorId(authorId);
		commentDto.setDate(dateTime);
		AnswerComment comment = answerCommentMapper.toComment(commentDto);
		answerCommentRepository.save(comment);
	}
	
	// Update question comment
	public void updateQuestionComment(@Valid CommentRequest commentRequest, int commentId) {
		QuestionComment comment = questionCommentRepository.findById(commentId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		QuestionCommentDto commentDto = new QuestionCommentDto();
		commentDto.setId(comment.getId());
		commentDto.setAuthorId(comment.getAuthorId());
		commentDto.setDate(dateTime);
		commentDto.setQuestionId(comment.getQuestionId());
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
		QuestionComment updatedComment = questionCommentMapper.toComment(commentDto);
		questionCommentRepository.save(updatedComment);
	}
	
	// Update answer comment
	public void updateAnswerComment(@Valid CommentRequest commentRequest, int commentId) {
		AnswerComment comment = answerCommentRepository.findById(commentId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerCommentDto commentDto = new AnswerCommentDto();
		commentDto.setId(comment.getId());
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
		AnswerComment updatedComment = answerCommentMapper.toComment(commentDto);
		answerCommentRepository.save(updatedComment);
	}
	
	// Delete question comment
	public void deleteQuestionComment(int commentId) {
		questionCommentRepository.deleteById(commentId);
	}
	
	// Delete answer comment
	public void deleteAnswerComment(int commentId) {
		answerCommentRepository.deleteById(commentId);
	}

}
