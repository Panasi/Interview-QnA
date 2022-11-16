package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.dao.CategoryRepository;
import com.panasi.interview_questions.dao.QaARepository;
import com.panasi.interview_questions.entity.QuestionAndAnswer;
import com.panasi.interview_questions.entity.Category;

@Service
public class QaAServiceImp implements QaAService {
	
	@Autowired
	private QaARepository qaARepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// QaA Service
	
	public List<QuestionAndAnswer> getAllQuestionsAndAnswers() {
		return qaARepository.findAll();
	}
	
	public QuestionAndAnswer getQuestionAndAnswerById(int id) {
		Optional<QuestionAndAnswer> optional = qaARepository.findById(id);
		QuestionAndAnswer questionAndAnswer = null;
		if (optional.isPresent()) {
			questionAndAnswer = optional.get();
		}
		return questionAndAnswer;
	}
	
	public void saveQuestionAndAnswer(QuestionAndAnswer questionAndAnswer) {
		qaARepository.save(questionAndAnswer);
	}
	
	public void deleteQuestionAndAnswer(int id) {
		qaARepository.deleteById(id);
	}
	
	// Category Service
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Category getCategoryById(int id) {
		Optional<Category> optional = categoryRepository.findById(id);
		Category category = null;
		if (optional.isPresent()) {
			category = optional.get();
		}
		return category;
	}
	
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public void deleteCategory(int id) {
		categoryRepository.deleteById(id);
	}

}
