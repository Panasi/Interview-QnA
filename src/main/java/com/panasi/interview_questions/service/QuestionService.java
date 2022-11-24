package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.service.mappers.CategoryMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final CategoryMapper categoryMapper;
	
	
	public List<QuestionDto> getAllQuestions() {
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAll());
		return allQuestionDtos;
	}
	
	public List<QuestionDto> getAllQuestionsByCategory(int id) {
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByCategoryId(id));
		return allQuestionDtos;
	}
	
	public QuestionDto getQuestionById(int id) {
		Optional<Question> optional = questionRepository.findById(id);
		QuestionDto question = null;
		if (optional.isPresent()) {
			question = questionMapper.toQuestionDto(optional.get());
		}
		return question;
	}
	
	public void createQuestion(QuestionDto questionDto) {
		Question question = questionMapper.toQuestion(questionDto);
		questionRepository.save(question);
	}
	
	public void updateQuestion(QuestionDto questionDto, int id) {
		Question question = questionRepository.findById(id).get();
		questionDto.setId(id);
		if (Objects.isNull(questionDto.getQuestion())) {
			questionDto.setQuestion(question.getQuestion());
		}
		if (Objects.isNull(questionDto.getAnswer())) {
			questionDto.setAnswer(question.getAnswer());
		}
		if (Objects.isNull(questionDto.getCategory())) {
			CategoryDto categoryDto = categoryMapper.toCategoryDto(question.getCategory());
			questionDto.setCategory(categoryDto);
		}
		Question updatedQuestion = questionMapper.toQuestion(questionDto);
		questionRepository.save(updatedQuestion);
	}
	
	public void deleteQuestion(int id) {
		questionRepository.deleteById(id);
	}

}
