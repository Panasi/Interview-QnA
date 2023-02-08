package com.panasi.interview_questions.service.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.PdfEditor;
import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.repository.entity.Question;
import com.panasi.interview_questions.service.QuestionService;

@Service
public class UserQuestionService extends QuestionService {
	
	// Return all questions and answers
	public List<FullQuestionDto> getQuestionsWithAnswers() {
		int currentUserId = Utils.getCurrentUserId();
		List<Question> questions = questionRepository.findAllPublicAndAuthorPrivate(currentUserId);
		List<FullQuestionDto> questionDtos = fullQuestionMapper.toFullQuestionDtos(questions);
		questionDtos.forEach(question -> {
			setQuestionRating(question);
			question.getAnswers().forEach(answer -> setAnswerRating(answer));
		});
		List<FullQuestionDto> sortedQuestions = sortFullQuestionDtos(questionDtos);
		return sortedQuestions;
	}
	
	// Return all questions and answers by category id
	public List<FullQuestionDto> getCategoryQuestionsWithAnswers(int categoryId) {
		int currentUserId = Utils.getCurrentUserId();
		List<Question> questions = questionRepository.findAllPublicAndAuthorPrivateByCategoryId(categoryId, currentUserId);
		List<FullQuestionDto> questionDtos = fullQuestionMapper.toFullQuestionDtos(questions);
		questionDtos.forEach(question -> {
			setQuestionRating(question);
			question.getAnswers().forEach(answer -> setAnswerRating(answer));
		});
		List<FullQuestionDto> sortedQuestions = sortFullQuestionDtos(questionDtos);
		return sortedQuestions;
	}

	// Return questions from certain category
	public List<QuestionDto> getCategoryQuestions(int categoryId, String access) {
		int currentUserId = Utils.getCurrentUserId();
		List<Question> questions;
	    if (access.equals("public")) {
	        questions = questionRepository.findAllByCategoryIdAndIsPrivate(categoryId, false);
	    } else if (access.equals("private")) {
	        questions = questionRepository.findAllByCategoryIdAndIsPrivateAndAuthorId(categoryId, true, currentUserId);
	    } else {
	    	questions = questionRepository.findAllPublicAndAuthorPrivateByCategoryId(categoryId, currentUserId);
	    }
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(questions);
		questionDtos.forEach(question -> setQuestionRating(question));
		List<QuestionDto> sortedQuestions = sortQuestionDtos(questionDtos); 
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
		int currentUserId = Utils.getCurrentUserId();
		List<Question> questions;
		if (access.equals("public") || (access.equals("all") && authorId != currentUserId)) {
	        questions = questionRepository.findAllByAuthorIdAndIsPrivate(authorId, false);
	    } else if (access.equals("private") && authorId == currentUserId) {
	    	questions = questionRepository.findAllByAuthorIdAndIsPrivate(authorId, true);
	    } else if (access.equals("private") && authorId != currentUserId) {
	    	return null;
	    } else {
	    	questions = questionRepository.findAllByAuthorId(authorId);
	    }
		List<QuestionDto> questionDtos = questionMapper.toQuestionDtos(questions);
		questionDtos.forEach(question -> setQuestionRating(question));
		List<QuestionDto> sortedQuestions = sortQuestionDtos(questionDtos); 
	    return sortedQuestions;
	}
	
	// Return question with answers by id
	public FullQuestionDto getQuestionById(int questionId) {
		int currentUserId = Utils.getCurrentUserId();
		Question question = questionRepository.findById(questionId).get();
		if (question.getIsPrivate() && question.getAuthorId() != currentUserId) {
			return null;
		}
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
	public boolean updateQuestion(QuestionRequest questionRequest, int questionId) {
		int currentUserId = Utils.getCurrentUserId();
		Question question = questionRepository.findById(questionId).get();
		if (question.getAuthorId() == currentUserId) {
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
			return true;
		}
		return false;
	}
	
	// Create PDF file for all questions and answers
	public void createPDF() throws IOException {
		List<FullQuestionDto> questions = getQuestionsWithAnswers();
		String filePath = "./src/main/resources/temp/Questions And Answers.pdf";
		String title = "Questions and Asnwers";
		PdfEditor.createPDF(filePath, title, questions);
	}
		
	// Create PDF file for all questions and answers from category
	public void createPDF(int categoryId) throws IOException {
		List<FullQuestionDto> questions = getCategoryQuestionsWithAnswers(categoryId);
		String filePath = "./src/main/resources/temp/Questions And Answers.pdf";
		String categoryName = categoryRepository.findById(categoryId).get().getName();
		String title = "Questions and Asnwers from " + categoryName + " category";
		PdfEditor.createPDF(filePath, title, questions);	
	}

}
