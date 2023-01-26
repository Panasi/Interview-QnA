package com.panasi.interview_questions.service.admin;

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
public class AdminCommentService {
	
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
	
	// Return all author comments to all questions
	public List<QuestionCommentDto> getAllUserCommentsToQuestions(int authorId) {
		List<QuestionComment> allUserComments = questionCommentRepository.findAllByAuthorId(authorId);
		List<QuestionCommentDto> allUserCommentDtos = questionCommentMapper.toCommentDtos(allUserComments);
		return allUserCommentDtos;
	}
	
	// Return all author comments to all answers
	public List<AnswerCommentDto> getAllUserCommentsToAnswers(int authorId) {
		List<AnswerComment> allUserComments = answerCommentRepository.findAllByAuthorId(authorId);
		List<AnswerCommentDto> allUserCommentDtos = answerCommentMapper.toCommentDtos(allUserComments);
		return allUserCommentDtos;
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
	public void createQuestionComment(@Valid CommentRequest commentRequest, int questionId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		QuestionCommentDto commentDto = QuestionCommentDto.builder()
			.content(commentRequest.getContent())
			.rate(commentRequest.getRate())
			.questionId(questionId)
			.authorId(authorId)
			.date(dateTime)
			.build();
		QuestionComment comment = questionCommentMapper.toComment(commentDto);
		questionCommentRepository.save(comment);
	}
	
	// Add a new comment to answer
	public void createAnswerComment(@Valid CommentRequest commentRequest, int answerId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerCommentDto commentDto = AnswerCommentDto.builder()
			.content(commentRequest.getContent())
			.rate(commentRequest.getRate())
			.answerId(answerId)
			.authorId(authorId)
			.date(dateTime)
			.build();
		AnswerComment comment = answerCommentMapper.toComment(commentDto);
		answerCommentRepository.save(comment);
	}
	
	// Update question comment
	public void updateQuestionComment(@Valid CommentRequest commentRequest, int commentId) {
		QuestionComment comment = questionCommentRepository.findById(commentId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		comment.setDate(dateTime);
		if (Objects.nonNull(commentRequest.getContent())) {
			comment.setContent(commentRequest.getContent());
		}
		if (Objects.nonNull(commentRequest.getRate())) {
			comment.setRate(commentRequest.getRate());
		}
		questionCommentRepository.save(comment);
	}
	
	// Update answer comment
	public void updateAnswerComment(@Valid CommentRequest commentRequest, int commentId) {
		AnswerComment comment = answerCommentRepository.findById(commentId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		comment.setDate(dateTime);
		if (Objects.nonNull(commentRequest.getContent())) {
			comment.setContent(commentRequest.getContent());
		}
		if (Objects.nonNull(commentRequest.getRate())) {
			comment.setRate(commentRequest.getRate());
		}
		answerCommentRepository.save(comment);
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
