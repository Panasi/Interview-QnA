package com.panasi.interview_questions.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.CategoryDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.CategoryMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQuestionService {
	
	private final QuestionRepository questionRepository;
	private final CategoryRepository categoryRepository;
	private final QuestionMapper questionMapper;
	private final CategoryMapper categoryMapper;
	
	
	// Return questions from certain category
	public List<QuestionDto> getQuestionsFromCategory(int categoryId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Question> questions = questionRepository.findAllByCategoryIdAndUserId(categoryId, userDetails.getId());
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(questions);
		return questionDtos;
	}
	
	// Return questions from certain category and all its subcategories
	public List<QuestionDto> getQuestionsFromSubcategories(int categoryId, List<QuestionDto> result) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Question> questions = questionRepository.findAllByCategoryIdAndUserId(categoryId, userDetails.getId());
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(questions);
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
	
	// Return all user questions
	public List<QuestionDto> getAllUserQuestions(int authorId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<QuestionDto> questionDtos;
		if (authorId == userDetails.getId()) {
			questionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByAuthorId(authorId));
		} else {
			questionDtos = questionMapper.toQuestionDtos(questionRepository.findAllByAuthorIdAndIsPrivate(authorId, false));
		}
		return questionDtos;
	}
	
	// Return question by id
	public QuestionDto getQuestionById(int questionId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Question question = questionRepository.findById(questionId).get();
		if (question.getIsPrivate() && question.getAuthorId() != userDetails.getId()) {
			return null;
		}
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
	public boolean updateQuestion(QuestionRequest questionRequest, int questionId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Question question = questionRepository.findById(questionId).get();
		if (question.getAuthorId() == userDetails.getId()) {
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
			Question updatedQuestion = questionMapper.toQuestion(questionDto);
			questionRepository.save(updatedQuestion);
			return true;
		}
		return false;
	}

}
