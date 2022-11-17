package com.panasi.interview_questions.service;

import java.util.List;

import com.panasi.interview_questions.entity.QuestionAndAnswer;


public interface QaAService {
	
	public List<QuestionAndAnswer> getAllQuestionsAndAnswers();
	
	public QuestionAndAnswer getQuestionAndAnswerById(int id);
	
	public void saveQuestionAndAnswer(QuestionAndAnswer questionAndAnswer);
	
	public void deleteQuestionAndAnswer(int id);
	
}
