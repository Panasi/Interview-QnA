package com.panasi.interview_questions.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.FullQuestionMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminQuestionService {
	
	private final QuestionRepository questionRepository;
	private final CategoryRepository categoryRepository;
	private final QuestionMapper questionMapper;
	private final FullQuestionMapper fullQuestionMapper;
	private final Utils utils;
	
	
	// Return all questions
	public List<QuestionDto> getAllQuestions(String access) {
	    List<Question> questions;
	    if (access.equals("public")) {
	        questions = questionRepository.findAllByIsPrivate(false);
	    } else if (access.equals("private")) {
	        questions = questionRepository.findAllByIsPrivate(true);
	    } else {
	        questions = questionRepository.findAll();
	    }
	    List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questions);
	    allQuestionDtos.forEach(question -> utils.setQuestionRating(question));
	    return allQuestionDtos;
	}
	
	// Return questions from certain category
	public List<QuestionDto> getCategoryQuestions(int categoryId, String access) {
		List<Question> questions;
		if (access.equals("public")) {
	        questions = questionRepository.findAllByCategoryIdAndIsPrivate(categoryId, false);
	    } else if (access.equals("private")) {
	        questions = questionRepository.findAllByCategoryIdAndIsPrivate(categoryId, true);
	    } else {
	        questions = questionRepository.findAllByCategoryId(categoryId);
	    }
		List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questions);
		allQuestionDtos.forEach(question -> utils.setQuestionRating(question));
		return allQuestionDtos;
	}
	
	// Return questions from certain category and all its subcategories
	public List<QuestionDto> getSubcategoriesQuestions(int categoryId, String access, List<QuestionDto> allQuestionDtos) {
		List<QuestionDto> questionDtos = getCategoryQuestions(categoryId, access);
		allQuestionDtos.addAll(questionDtos);
		List<Category> allSubcategories = categoryRepository.findAllByParentId(categoryId);
		if (allSubcategories.isEmpty()) {
			return allQuestionDtos;
		}
		allSubcategories.forEach(subcategory -> {
			getSubcategoriesQuestions(subcategory.getId(), access, allQuestionDtos);
		});
		return allQuestionDtos;
	}
	
	// Return user questions
	public List<QuestionDto> getUserQuestions(int authorId, String access) {
		List<Question> questions;
	    if (access.equals("public")) {
	        questions = questionRepository.findAllByAuthorIdAndIsPrivate(authorId, false);
	    } else if (access.equals("private")) {
	        questions = questionRepository.findAllByAuthorIdAndIsPrivate(authorId, true);
	    } else {
	        questions = questionRepository.findAllByAuthorId(authorId);
	    }
	    List<QuestionDto> allQuestionDtos = questionMapper.toQuestionDtos(questions);
	    allQuestionDtos.forEach(question -> utils.setQuestionRating(question));
		return allQuestionDtos;
	}
	
	// Return question by id
	public FullQuestionDto getQuestionById(int questionId) {
		Question question = questionRepository.findById(questionId).get();
		FullQuestionDto	questionDto = fullQuestionMapper.toFullQuestionDto(question);
		questionDto.getAnswers().forEach(answer -> utils.setAnswerRating(answer));
		utils.setQuestionRating(questionDto);
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
		question.setDate(dateTime);
		if (Objects.nonNull(questionRequest.getName())) {
			question.setName(questionRequest.getName());
		}
		if (Objects.nonNull(questionRequest.getCategoryId())) {
			question.setCategoryId(questionRequest.getCategoryId());
		}
		if (Objects.nonNull(questionRequest.getIsPrivate())) {
			question.setIsPrivate(questionRequest.getIsPrivate());
		}
		questionRepository.save(question);
	}
	
	// Delete certain question
	public void deleteQuestion(int questionId) {
		questionRepository.deleteById(questionId);
	}

}
