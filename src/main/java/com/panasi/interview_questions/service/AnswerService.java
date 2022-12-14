package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

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
	public void createAnswer(AnswerDto answerDto) {
		Answer answer = mapper.toAnswer(answerDto);
		answerRepository.save(answer);
	}
	
	// Update certain answer
	public void updateAnswer(AnswerDto answerDto, int answerId) {
		Answer answer = answerRepository.findById(answerId).get();
		answerDto.setId(answerId);
		if (Objects.isNull(answerDto.getName())) {
			answerDto.setName(answer.getName());
		}
		if (Objects.isNull(answerDto.getQuestionId())) {
			answerDto.setQuestionId(answer.getQuestionId());
		}
		Answer updatedAnswer = mapper.toAnswer(answerDto);
		answerRepository.save(updatedAnswer);
	}
	
	// Delete certain answer
	public void deleteAnswer(int answerId) {
		answerRepository.deleteById(answerId);
	}

}
