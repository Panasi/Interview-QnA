package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final QuestionMapper mapper;
	
	
	public List<QuestionDto> getAllQuestions() {
		List<QuestionDto> allQuestionDtos = mapper.toQuestionDtos(questionRepository.findAll());
		return allQuestionDtos;
	}
	
	public List<QuestionDto> getAllQuestionsByCategory(int id) {
		List<QuestionDto> allQuestionDtos = mapper.toQuestionDtos(questionRepository.findAllByCategoryId(id));
		return allQuestionDtos;
	}
	
	public QuestionDto getQuestionById(int id) {
		Optional<Question> optional = questionRepository.findById(id);
		QuestionDto question = null;
		if (optional.isPresent()) {
			question = mapper.toQuestionDto(optional.get());
		}
		return question;
	}
	
	public void saveQuestion(QuestionDto questionDto) {
		Question question = mapper.toQuestion(questionDto);
		questionRepository.save(question);
	}
	
	public void deleteQuestion(int id) {
		questionRepository.deleteById(id);
	}

}
