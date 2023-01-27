package com.panasi.interview_questions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.FullAnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	protected final AnswerRepository answerRepository;
	protected final AnswerMapper answerMapper;
	protected final FullAnswerMapper fullAnswerMapper;
	protected final CommentService commentService;
	
	
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
