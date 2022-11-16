package com.panasi.interview_questions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.entity.QuestionAndAnswer;
import com.panasi.interview_questions.service.QaAService;

@RestController
@RequestMapping("/qaa")
public class QaAController {
	
	@Autowired
	private QaAService service;
	
	
	@GetMapping("/all")
	public List<QuestionAndAnswer> showAllQuestionsAndAnswers() {
		List<QuestionAndAnswer> allQuestionsAndAnswers = service.getAllQuestionsAndAnswers();
		return allQuestionsAndAnswers;
	}
	@GetMapping("/all/{id}")
	public QuestionAndAnswer showQuestionAndAnswer(@PathVariable int id) {
		QuestionAndAnswer questionAndAnswer = service.getQuestionAndAnswerById(id);
		return questionAndAnswer;
	}
	
	@PostMapping("/all")
	public QuestionAndAnswer addNewQuestionAndAnswer(@RequestBody QuestionAndAnswer questionAndAnswer) {
		service.saveQuestionAndAnswer(questionAndAnswer);
		return questionAndAnswer;
	}
	
	@PutMapping("/all")
	public QuestionAndAnswer updateQuestionAndAnswer(@RequestBody QuestionAndAnswer questionAndAnswer) {
		service.saveQuestionAndAnswer(questionAndAnswer);
		return questionAndAnswer;
	}
	
	@DeleteMapping("/all/{id}")
	public String deleteQuestionAndAnswer(@PathVariable int id) {
		service.deleteQuestionAndAnswer(id);
		return "QuestionAndAnswer " + id + " was deleted!";
	}

}
