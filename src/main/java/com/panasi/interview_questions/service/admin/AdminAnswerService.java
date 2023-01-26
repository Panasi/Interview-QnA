package com.panasi.interview_questions.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.FullAnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerMapper answerMapper;
	private final FullAnswerMapper fullAnswerMapper;
	private final Utils utils;

	
	// Return all answers
	public List<AnswerDto> getAllAnswers(String access) {
		List<Answer> answers;
		if (access.equals("public")) {
			answers = answerRepository.findAllByIsPrivate(false);
		} else if (access.equals("private")) {
			answers = answerRepository.findAllByIsPrivate(true);
		} else {
			answers = answerRepository.findAll();
		}
		List<AnswerDto> allAnswerDtos = answerMapper.toAnswerDtos(answers);
		allAnswerDtos.stream().forEach(answer -> utils.setAnswerRating(answer));
		return allAnswerDtos;
	}
	
	// Return all user answers
	public List<AnswerDto> getAllUserAnswers(int authorId, String access) {
		List<Answer> answers;
		if (access.equals("public")) {
			answers = answerRepository.findAllByAuthorIdAndIsPrivate(authorId, false);
		} else if (access.equals("private")) {
			answers = answerRepository.findAllByAuthorIdAndIsPrivate(authorId, true);
		} else {
			answers = answerRepository.findAllByAuthorId(authorId);
		}
		List<AnswerDto> allAnswerDtos = answerMapper.toAnswerDtos(answers);
		allAnswerDtos.stream().forEach(answer -> utils.setAnswerRating(answer));
		return allAnswerDtos;
	}
	
	// Return answer by id
	public FullAnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		FullAnswerDto answerDto = fullAnswerMapper.toFullAnswerDto(answer);
		utils.setAnswerRating(answerDto);
		return answerDto;
	}
	
	// Add a new answer
	public void createAnswer(AnswerRequest answerRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorName = userDetails.getUsername();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerDto answerDto = AnswerDto.builder()
			.name(answerRequest.getName())
			.questionId(answerRequest.getQuestionId())
			.isPrivate(answerRequest.getIsPrivate())
			.authorName(authorName)
			.authorId(authorId)
			.date(dateTime)
			.build();
		Answer answer = answerMapper.toAnswer(answerDto);
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
