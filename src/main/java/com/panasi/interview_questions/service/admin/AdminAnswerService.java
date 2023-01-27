package com.panasi.interview_questions.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.service.AnswerService;

@Service
public class AdminAnswerService extends AnswerService {
	
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
		allAnswerDtos.stream().forEach(answer -> setAnswerRating(answer));
		List<AnswerDto> sortedAnswers = sortAnswerDtos(allAnswerDtos);
		return sortedAnswers;
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
		List<AnswerDto> answerDtos = answerMapper.toAnswerDtos(answers);
		answerDtos.stream().forEach(answer -> setAnswerRating(answer));
		List<AnswerDto> sortedAnswers = sortAnswerDtos(answerDtos);
		return sortedAnswers;
	}
	
	// Return answer by id
	public FullAnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		FullAnswerDto answerDto = fullAnswerMapper.toFullAnswerDto(answer);
		setAnswerRating(answerDto);
		return answerDto;
	}
	
	// Add a new answer
	public void createAnswer(AnswerRequest answerRequest) {
		String currentUserName = Utils.getCurrentUserName();
		int currentUserId = Utils.getCurrentUserId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerDto answerDto = AnswerDto.builder()
			.name(answerRequest.getName())
			.questionId(answerRequest.getQuestionId())
			.isPrivate(answerRequest.getIsPrivate())
			.authorName(currentUserName)
			.authorId(currentUserId)
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
