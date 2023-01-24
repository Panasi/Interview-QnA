package com.panasi.interview_questions.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.FullAnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerCommentRepository commentRepository;
	private final AnswerMapper mapper;
	private final FullAnswerMapper fullMapper;
	
	
	// Set answer rating
	public void setAnswerRating(AnswerDto answer) {
		int answerId = answer.getId();
		List<AnswerComment> comments = commentRepository.findAllByAnswerId(answerId);
		if (comments.isEmpty()) {
			answer.setRating(null);
		} else {
			Double rating = commentRepository.getRating(answerId);
			answer.setRating(rating);
		}
	}
	
	// Set answer rating
	public void setAnswerRating(FullAnswerDto answer) {
		int answerId = answer.getId();
		List<AnswerComment> comments = commentRepository.findAllByAnswerId(answerId);
		if (comments.isEmpty()) {
			answer.setRating(null);
		} else {
			Double rating = commentRepository.getRating(answerId);
			answer.setRating(rating);
		}
	}
	
	// Return all answers
	public List<AnswerDto> getAllAnswers() {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAll());
		allAnswerDtos.stream().forEach(answer -> setAnswerRating(answer));
		return allAnswerDtos;
	}
	
	// Return all public answers
	public List<AnswerDto> getAllPublicAnswers() {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByIsPrivate(false));
		allAnswerDtos.stream().forEach(answer -> setAnswerRating(answer));
		return allAnswerDtos;
	}
	
	// Return all user answers
	public List<AnswerDto> getAllUserAnswers(int authorId) {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByAuthorId(authorId));
		allAnswerDtos.stream().forEach(answer -> setAnswerRating(answer));
		return allAnswerDtos;
	}
	
	// Return answer by id
	public FullAnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		FullAnswerDto answerDto = fullMapper.toFullAnswerDto(answer);
		setAnswerRating(answerDto);
		return answerDto;
	}
	
	// Add a new answer
	public void createAnswer(AnswerRequest answerRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorName = userDetails.getUsername();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerDto answerDto = new AnswerDto();
		answerDto.setName(answerRequest.getName());
		answerDto.setQuestionId(answerRequest.getQuestionId());
		answerDto.setIsPrivate(answerRequest.getIsPrivate());
		answerDto.setAuthorName(authorName);
		answerDto.setAuthorId(authorId);
		answerDto.setDate(dateTime);
		Answer answer = mapper.toAnswer(answerDto);
		answerRepository.save(answer);
	}
	
	// Update certain answer
	public void updateAnswer(AnswerRequest answerRequest, int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		answer.setDate(dateTime);
		if (Objects.nonNull(answerRequest.getName())) {
			answer.setName(answerRequest.getName());
		}
		if (Objects.nonNull(answerRequest.getQuestionId())) {
			answer.setQuestionId(answerRequest.getQuestionId());
		}
		if (Objects.nonNull(answerRequest.getIsPrivate())) {
			answer.setIsPrivate(answerRequest.getIsPrivate());
		}
		answerRepository.save(answer);
	}
	
	// Delete certain answer
	public void deleteAnswer(int answerId) {
		answerRepository.deleteById(answerId);
	}

}
