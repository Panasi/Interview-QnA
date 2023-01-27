package com.panasi.interview_questions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionCommentRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.repository.entity.QuestionComment;
import com.panasi.interview_questions.service.mappers.FullQuestionMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

@Service
public class QuestionService {
	
	@Autowired
	protected QuestionRepository questionRepository;
	@Autowired
	protected CategoryRepository categoryRepository;
	@Autowired
	protected QuestionMapper questionMapper;
	@Autowired
	protected FullQuestionMapper fullQuestionMapper;
	@Autowired
	private QuestionCommentRepository questionCommentRepository;
	@Autowired
	private AnswerCommentRepository answerCommentRepository;
	
	// Set full question rating
	public void setQuestionRating(FullQuestionDto question) {
		int questionId = question.getId();
		List<QuestionComment> comments = questionCommentRepository.findAllByQuestionId(questionId);
		if (comments.isEmpty()) {
			question.setRating(null);
		} else {
			Double rating = questionCommentRepository.getRating(questionId);
			Double roundedRating = Math.ceil(rating * 100) / 100;
			question.setRating(roundedRating);
		}
	}
		
	// Set question rating
	public void setQuestionRating(QuestionDto question) {
		int questionId = question.getId();
		List<QuestionComment> comments = questionCommentRepository.findAllByQuestionId(questionId);
		if (comments.isEmpty()) {
			question.setRating(null);
		} else {
			Double rating = questionCommentRepository.getRating(questionId);
			Double roundedRating = Math.ceil(rating * 100) / 100;
			question.setRating(roundedRating);
		}
	}
	
	// Set answer rating
	public void setAnswerRating(FullAnswerDto answer) {
		int answerId = answer.getId();
		List<AnswerComment> comments = answerCommentRepository.findAllByAnswerId(answerId);
		if (comments.isEmpty()) {
			answer.setRating(null);
		} else {
			Double rating = answerCommentRepository.getRating(answerId);
			Double roundedRating = Math.ceil(rating * 100) / 100;
			answer.setRating(roundedRating);
		}
	}
	
	// Sort questions
	public List<QuestionDto> sortQuestionDtos(List<QuestionDto> questionDtos) {
	    List<QuestionDto> sortedQuestions = new ArrayList<>(questionDtos);
	    sortedQuestions.sort((q1,q2) -> {
	        int compare = q1.getCategoryId().compareTo(q2.getCategoryId());
	        if (compare == 0) {
	            compare = Boolean.compare(q1.getIsPrivate(), q2.getIsPrivate());
	            if (compare == 0) {
	                compare = Optional.ofNullable(q1.getRating()).orElse(Double.MIN_VALUE)
	                	.compareTo(Optional.ofNullable(q2.getRating()).orElse(Double.MIN_VALUE));
	                if (compare == 0) {
	                    compare = q1.getDate().compareTo(q2.getDate());
	                }
	            }
	        }
	        return compare;
	    });
	    return sortedQuestions;
	}
	
}
