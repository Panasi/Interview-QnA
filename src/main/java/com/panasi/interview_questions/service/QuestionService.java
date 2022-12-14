package com.panasi.interview_questions.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
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
	
	// Return all public questions
	public List<QuestionDto> getAllPublicQuestions() {
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByIsPrivate(false));
		return allQuestionDtos;
	}
	
	// Return all private questions
	public List<QuestionDto> getAllPrivateQuestions() {
		List<QuestionDto> allPrivateQuestionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByIsPrivate(true));
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int authorId = userDetails.getId();
		List<QuestionDto> privateQuestionsDtos = allPrivateQuestionDtos.stream()
                .filter(question -> question.getAuthorId() == authorId)
                .collect(Collectors.toList());
		return privateQuestionsDtos;
	}
	
	// Return question by id
	public QuestionDto getQuestionById(int questionId) {
		Question question = questionRepository.findById(questionId).get();
		QuestionDto	questionDto = questionMapper.toQuestionDto(question);
		return questionDto;
	}
	
	// Add a new question
	public void createQuestion(QuestionRequest questionRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorName = userDetails.getUsername();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now(); 
		QuestionDto questionDto = new QuestionDto();
		questionDto.setName(questionRequest.getName());
		questionDto.setCategoryId(questionRequest.getCategoryId());
		questionDto.setIsPrivate(questionRequest.getIsPrivate());
		questionDto.setAuthorName(authorName);
		questionDto.setAuthorId(authorId);
		questionDto.setDate(dateTime);
		Question question = questionMapper.toQuestion(questionDto);
		questionRepository.save(question);
	}
	
	// Update certain question
	public void updateQuestion(QuestionRequest questionRequest, int questionId) {
		Question question = questionRepository.findById(questionId).get();
		LocalDateTime dateTime = LocalDateTime.now();
		QuestionDto questionDto = new QuestionDto();
		questionDto.setId(questionId);
		questionDto.setAuthorName(question.getAuthorName());
		questionDto.setDate(dateTime);
		if (Objects.isNull(questionRequest.getName())) {
			questionDto.setName(question.getName());
		} else {
			questionDto.setName(questionRequest.getName());
		}
		if (Objects.isNull(questionRequest.getCategoryId())) {
			questionDto.setCategoryId(question.getCategoryId());
		} else {
			questionDto.setCategoryId(questionRequest.getCategoryId());
		}
		if (Objects.isNull(questionRequest.getIsPrivate())) {
			questionDto.setIsPrivate(question.getIsPrivate());
		} else {
			questionDto.setIsPrivate(questionRequest.getIsPrivate());
		}
		List<AnswerDto> answerDtos = answerMapper.toAnswerDtos(question.getAnswers());
		questionDto.setAnswers(answerDtos);
		Question updatedQuestion = questionMapper.toQuestion(questionDto);
		questionRepository.save(updatedQuestion);
	}
	
	// Delete certain question
	public void deleteQuestion(int questionId) {
		questionRepository.deleteById(questionId);
	}

}
