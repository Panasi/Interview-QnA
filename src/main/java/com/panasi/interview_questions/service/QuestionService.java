package com.panasi.interview_questions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.entity.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}
	
	public List<Question> getAllQuestionsByCategory(int id) {
		return questionRepository.findAllByCategoryId(id);
	}
	
	public Question getQuestionById(int id) {
		Optional<Question> optional = questionRepository.findById(id);
		Question question = null;
		if (optional.isPresent()) {
			question = optional.get();
		}
		return question;
	}
	
	public void saveQuestion(Question question) {
		questionRepository.save(question);
	}
	
	public void deleteQuestion(int id) {
		questionRepository.deleteById(id);
	}

}
