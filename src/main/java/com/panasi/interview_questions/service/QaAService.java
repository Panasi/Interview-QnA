package com.panasi.interview_questions.service;

import java.util.List;

import com.panasi.interview_questions.entity.Category;
import com.panasi.interview_questions.entity.QuestionAndAnswer;


public interface QaAService {
	
	public List<QuestionAndAnswer> getAllQuestionsAndAnswers();
	
	public QuestionAndAnswer getQuestionAndAnswerById(int id);
	
	public void saveQuestionAndAnswer(QuestionAndAnswer questionAndAnswer);
	
	public void deleteQuestionAndAnswer(int id);
	
	public List<Category> getAllCategories();
	
	public Category getCategoryById(int id);
	
	public void saveCategory(Category category);
	
	public void deleteCategory(int id);
}
