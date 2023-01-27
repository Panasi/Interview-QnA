package com.panasi.interview_questions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.FullAnswerMapper;

@Service
public class AnswerService {
	
	@Autowired
	protected AnswerRepository answerRepository;
	@Autowired
	protected AnswerMapper answerMapper;
	@Autowired
	protected FullAnswerMapper fullAnswerMapper;
	@Autowired
	private AnswerCommentRepository answerCommentRepository;
	
	// Set full answer rating
	public void setAnswerRating(AnswerDto answer) {
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
	
	// Sort Answers
	public List<AnswerDto> sortAnswerDtos(List<AnswerDto> answerDtos) {
	    List<AnswerDto> sortedAnswers = new ArrayList<>(answerDtos);
	    sortedAnswers.sort((q1,q2) -> {
	        int compare = q1.getQuestionId().compareTo(q2.getQuestionId());
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
	    return sortedAnswers;
	}

}
