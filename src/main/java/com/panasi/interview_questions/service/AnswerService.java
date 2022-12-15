package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.service.mappers.AnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerMapper mapper;
	
	// Return all answers
	public List<AnswerDto> getAllAnswers() {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAll());
		return allAnswerDtos;
	}
	
	// Return all answers to the question
	public List<AnswerDto> getAllAnswersToQuestion(int questionId) {
		List<AnswerDto> allAnswerDtos = mapper.toAnswerDtos(answerRepository.findAllByQuestionId(questionId));
		return allAnswerDtos;
	}
	
	// Return answer by id
	public AnswerDto getAnswerById(int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		AnswerDto answerDto = mapper.toAnswerDto(answer);
		return answerDto;
	}
	
	// Add a new answer
	public void createAnswer(AnswerRequest answerRequest) {
		AnswerDto answerDto = new AnswerDto();
		answerDto.setName(answerRequest.getName());
		answerDto.setQuestionId(answerRequest.getQuestionId());
		Answer answer = mapper.toAnswer(answerDto);
		answerRepository.save(answer);
	}
	
	// Update certain answer
	public void updateAnswer(AnswerRequest answerRequest, int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		AnswerDto answerDto = new AnswerDto();
		answerDto.setId(answerId);
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
		Answer updatedAnswer = mapper.toAnswer(answerDto);
		answerRepository.save(updatedAnswer);
	}
	
	// Delete certain answer
	public void deleteAnswer(int answerId) {
		answerRepository.deleteById(answerId);
	}

}
