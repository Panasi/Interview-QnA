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
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerCommentRepository commentRepository;
	private final AnswerMapper mapper;
	
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
	public List<AnswerDto> getUserAnswers(int authorId) {
		List<AnswerDto> answerDtos = mapper.toAnswerDtos(answerRepository.findAllByAuthorId(authorId));
		answerDtos.stream().forEach(answer -> setAnswerRating(answer));
		return answerDtos;
	}
	
	// Return answer by id
	public AnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		AnswerDto answerDto = mapper.toAnswerDto(answer);
		List<AnswerComment> comments = commentRepository.findAllByAnswerId(answerId);
		if (comments.isEmpty()) {
			answerDto.setRating(null);
		} else {
			Double rating = commentRepository.getRating(answerId);
			answerDto.setRating(rating);
		}
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
		AnswerDto answerDto = new AnswerDto();
		answerDto.setId(answerId);
		answerDto.setAuthorName(answer.getAuthorName());
		answerDto.setAuthorId(answer.getAuthorId());
		answerDto.setDate(dateTime);
		if (Objects.isNull(answerRequest.getName())) {
			answerDto.setName(answer.getName());
		} else {
			answerDto.setName(answerRequest.getName());
		}
		if (Objects.isNull(answerRequest.getQuestionId())) {
			answerDto.setQuestionId(answer.getQuestionId());
		} else {
			answerDto.setQuestionId(answerRequest.getQuestionId());
		}
		if (Objects.isNull(answerRequest.getIsPrivate())) {
			answerDto.setIsPrivate(answer.getIsPrivate());
		} else {
			answerDto.setIsPrivate(answerRequest.getIsPrivate());
		}
		Answer updatedAnswer = mapper.toAnswer(answerDto);
		answerRepository.save(updatedAnswer);
	}
	
	// Delete certain answer
	public void deleteAnswer(int answerId) {
		answerRepository.deleteById(answerId);
	}

}
