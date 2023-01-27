package com.panasi.interview_questions.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.service.QuestionService;

@Service
public class AdminQuestionService extends QuestionService {

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
	    allQuestionDtos.forEach(question -> setQuestionRating(question));
	    List<QuestionDto> sortedQuestions = sortQuestionDtos(allQuestionDtos); 
	    return sortedQuestions;
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
		allQuestionDtos.forEach(question -> setQuestionRating(question));
		List<QuestionDto> sortedQuestions = sortQuestionDtos(allQuestionDtos); 
	    return sortedQuestions;
	}
	
	// Return questions from certain category and all its subcategories
	public List<QuestionDto> getSubcategoriesQuestions(int categoryId, String access, List<QuestionDto> allQuestionDtos) {
		List<QuestionDto> questionDtos = getCategoryQuestions(categoryId, access);
		allQuestionDtos.addAll(questionDtos);
		List<Category> allSubcategories = categoryRepository.findAllByParentId(categoryId);
		if (allSubcategories.isEmpty()) {
			List<QuestionDto> sortedQuestions = sortQuestionDtos(allQuestionDtos);
			return sortedQuestions;
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
	    allQuestionDtos.forEach(question -> setQuestionRating(question));
	    List<QuestionDto> sortedQuestions = sortQuestionDtos(allQuestionDtos); 
	    return sortedQuestions;
	}
	
	// Return question by id
	public FullQuestionDto getQuestionById(int questionId) {
		Question question = questionRepository.findById(questionId).get();
		FullQuestionDto	questionDto = fullQuestionMapper.toFullQuestionDto(question);
		questionDto.getAnswers().forEach(answer -> setAnswerRating(answer));
		setQuestionRating(questionDto);
		return questionDto;
	}
	
	// Add a new question
	public void createQuestion(QuestionRequest questionRequest) {
		String currentUserName = Utils.getCurrentUserName();
		int currentUserId = Utils.getCurrentUserId();
		LocalDateTime dateTime = LocalDateTime.now(); 
		QuestionDto questionDto = QuestionDto.builder()
			.name(questionRequest.getName())
			.categoryId(questionRequest.getCategoryId())
			.isPrivate(questionRequest.getIsPrivate())
			.authorName(currentUserName)
			.authorId(currentUserId)
			.date(dateTime)
			.build();
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
