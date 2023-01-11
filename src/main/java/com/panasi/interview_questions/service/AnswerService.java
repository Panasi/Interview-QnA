package com.panasi.interview_questions.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerCommentRepository commentRepository;
	private final AnswerMapper mapper;
	
	// Set answer rating
	public void setAnswerRating(List<AnswerDto> answerDtos) {
		answerDtos.stream().forEach(answer -> {
			int answerId = answer.getId();
			double rating = commentRepository.getRating(answerId);
			answer.setRating(rating);
		});
	}
	
	// Return all answers
	public List<AnswerDto> getAllAnswers() {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAll());
		setAnswerRating(allAnswerDtos);
		return allAnswerDtos;
	}
	
	// Return all answers to the question
	public List<AnswerDto> getAllAnswersToQuestion(int questionId) {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByQuestionId(questionId));
		setAnswerRating(allAnswerDtos);
		return allAnswerDtos;
	}
	
	// Return all public answers
	public List<AnswerDto> getAllPublicAnswers() {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByIsPrivate(false));
		setAnswerRating(allAnswerDtos);
		return allAnswerDtos;
	}
	
	// Return all private answers
	public List<AnswerDto> getAllPrivateAnswers() {
		List<AnswerDto> allPrivateAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByIsPrivate(true));
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		List<AnswerDto> privateAnswersDtos = allPrivateAnswerDtos.stream()
                .filter(answer -> answer.getAuthorId() == authorId)
                .collect(Collectors.toList());
		setAnswerRating(privateAnswersDtos);
		return privateAnswersDtos;
	}
	
	// Return answer by id
	public AnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		AnswerDto answerDto = mapper.toAnswerDto(answer);
		double rating = commentRepository.getRating(answerId);
		answerDto.setRating(rating);
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
