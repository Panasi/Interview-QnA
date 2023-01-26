package com.panasi.interview_questions.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.panasi.interview_questions.payload.CommentRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.QuestionCommentRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.repository.entity.QuestionComment;
import com.panasi.interview_questions.service.mappers.AnswerCommentMapper;
import com.panasi.interview_questions.service.mappers.QuestionCommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class UserCommentService {
	
	private final QuestionCommentRepository questionCommentRepository;
	private final AnswerCommentRepository answerCommentRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final QuestionCommentMapper questionCommentMapper;
	private final AnswerCommentMapper answerCommentMapper;
	
	
	// Return all comments to question
	public List<QuestionCommentDto> getAllCommentsToQuestion(int questionId) {
		int currentUserId = Utils.getCurrentUserId();
		Question question = questionRepository.findById(questionId).get();
		if (question.getIsPrivate() == false || currentUserId == question.getAuthorId()) {
			List<QuestionComment> allComments = questionCommentRepository.findAllByQuestionId(questionId);
			List<QuestionCommentDto> allCommentDtos = questionCommentMapper.toCommentDtos(allComments);
			return allCommentDtos;
		}
		return null;
	}
	
	// Return all comments to answer
	public List<AnswerCommentDto> getAllCommentsToAnswer(int answerId) {
		int currentUserId = Utils.getCurrentUserId();
		Answer answer = answerRepository.findById(answerId).get();
		if (answer.getIsPrivate() == false || currentUserId == answer.getAuthorId()) {
			List<AnswerComment> allComments = answerCommentRepository.findAllByAnswerId(answerId);
			List<AnswerCommentDto> allCommentDtos = answerCommentMapper.toCommentDtos(allComments);
			return allCommentDtos;
		}
		return null;
	}
	
	
	// Return question comment by id
	public QuestionCommentDto getQuestionCommentById(int commentId) {
		int currentUserId = Utils.getCurrentUserId();
		QuestionComment comment = questionCommentRepository.findById(commentId).get();
		Question question = questionRepository.findById(comment.getQuestionId()).get();
		if (question.getIsPrivate() == false || currentUserId == question.getAuthorId()) {
			QuestionCommentDto commentDto = questionCommentMapper.toCommentDto(comment);
			return commentDto;
		}
		return null;
	}
	
	// Return answer comment by id
	public AnswerCommentDto getAnswerCommentById(int commentId) {
		int currentUserId = Utils.getCurrentUserId();
		AnswerComment comment = answerCommentRepository.findById(commentId).get();
		Answer answer = answerRepository.findById(comment.getAnswerId()).get();
		if (answer.getIsPrivate() == false || currentUserId == answer.getAuthorId()) {
			AnswerCommentDto commentDto = answerCommentMapper.toCommentDto(comment);
			return commentDto;
		}
		return null;
	}
	
	// Add a new comment to question
	public boolean createQuestionComment(@Valid CommentRequest commentRequest, int questionId) {
		int currentUserId = Utils.getCurrentUserId();
		Question question = questionRepository.findById(questionId).get();
		if (question.getIsPrivate() == false || currentUserId == question.getAuthorId()) {
			LocalDateTime dateTime = LocalDateTime.now();
			QuestionCommentDto commentDto = QuestionCommentDto.builder()
				.content(commentRequest.getContent())
				.rate(commentRequest.getRate())
				.questionId(questionId)
				.authorId(currentUserId)
				.date(dateTime)
				.build();
			QuestionComment comment = questionCommentMapper.toComment(commentDto);
			questionCommentRepository.save(comment);
			return true;
		}
		return false;
		
	}
	
	// Add a new comment to answer
	public boolean createAnswerComment(@Valid CommentRequest commentRequest, int answerId) {
		int currentUserId = Utils.getCurrentUserId();
		Answer answer = answerRepository.findById(answerId).get();
		if (answer.getIsPrivate() == false || currentUserId == answer.getAuthorId()) {
			LocalDateTime dateTime = LocalDateTime.now();
			AnswerCommentDto commentDto = AnswerCommentDto.builder()
				.content(commentRequest.getContent())
				.rate(commentRequest.getRate())
				.answerId(answerId)
				.authorId(currentUserId)
				.date(dateTime)
				.build();
			AnswerComment comment = answerCommentMapper.toComment(commentDto);
			answerCommentRepository.save(comment);
			return true;
		}
		return false;
	}
	
	// Update question comment
	public boolean updateQuestionComment(@Valid CommentRequest commentRequest, int commentId) {
		int currentUserId = Utils.getCurrentUserId();
		QuestionComment comment = questionCommentRepository.findById(commentId).get();
		if (currentUserId == comment.getAuthorId()) {
			LocalDateTime dateTime = LocalDateTime.now();
			comment.setDate(dateTime);
			if (Objects.nonNull(commentRequest.getContent())) {
				comment.setContent(commentRequest.getContent());
			}
			if (Objects.nonNull(commentRequest.getRate())) {
				comment.setRate(commentRequest.getRate());
			}
			questionCommentRepository.save(comment);
			return true;
		}
		return false;
		
	}
	
	// Update answer comment
	public boolean updateAnswerComment(@Valid CommentRequest commentRequest, int commentId) {
		int currentUserId = Utils.getCurrentUserId();
		AnswerComment comment = answerCommentRepository.findById(commentId).get();
		if (currentUserId == comment.getAuthorId()) {
			LocalDateTime dateTime = LocalDateTime.now();
			comment.setDate(dateTime);
			if (Objects.nonNull(commentRequest.getContent())) {
				comment.setContent(commentRequest.getContent());
			}
			if (Objects.nonNull(commentRequest.getRate())) {
				comment.setRate(commentRequest.getRate());
			}
			answerCommentRepository.save(comment);
			return true;
		}
		return false;
	}

}
