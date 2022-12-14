package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.CategoryMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final CategoryRepository categoryRepository;
	private final QuestionMapper questionMapper;
	private final CategoryMapper categoryMapper;
	private final AnswerMapper answerMapper;
	
	
	// Return all questions
	public List<QuestionDto> getAllQuestions() {
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAll());
		return allQuestionDtos;
	}
	
	// Return questions from certain category
	public List<QuestionDto> getQuestionsFromCategory(int categoryId) {
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByCategoryId(categoryId));
		return allQuestionDtos;
	}
	
	// Return questions from certain category and all its subcategories
	public List<QuestionDto> getQuestionsFromSubcategories(int categoryId, List<QuestionDto> result) {
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByCategoryId(categoryId));
		result.addAll(questionDtos);
		List<CategoryDto> allSubcategoryDtos = categoryMapper.toCategoryDtos(categoryRepository.findAllByParentId(categoryId));
		if (allSubcategoryDtos.isEmpty()) {
			return result;
		}
		allSubcategoryDtos.forEach(subcategory -> {
			getQuestionsFromSubcategories(subcategory.getId(), result);
		});
		return result;
	}
	
	// Return question by id
	public QuestionDto getQuestionById(int questionId) {
		Question question = questionRepository.findById(questionId).get();
		QuestionDto	questionDto = questionMapper.toQuestionDto(question);
		return questionDto;
	}
	
	// Add a new question
	public void createQuestion(QuestionDto questionDto) {
		Question question = questionMapper.toQuestion(questionDto);
		questionRepository.save(question);
	}
	
	// Update certain question
	public void updateQuestion(QuestionDto questionDto, int questionId) {
		Question question = questionRepository.findById(questionId).get();
		questionDto.setId(questionId);
		if (Objects.isNull(questionDto.getName())) {
			questionDto.setName(question.getName());
		}
		if (Objects.isNull(questionDto.getAnswers())) {
			List<AnswerDto> answerDtos = answerMapper.toAnswerDtos(question.getAnswers());
			questionDto.setAnswers(answerDtos);
		}
		if (Objects.isNull(questionDto.getCategory())) {
			CategoryDto categoryDto = categoryMapper.toCategoryDto(question.getCategory());
			questionDto.setCategory(categoryDto);
		}
		Question updatedQuestion = questionMapper.toQuestion(questionDto);
		questionRepository.save(updatedQuestion);
	}
	
	// Delete certain question
	public void deleteQuestion(int questionId) {
		questionRepository.deleteById(questionId);
	}

}
