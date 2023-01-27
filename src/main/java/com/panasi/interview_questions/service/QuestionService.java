package com.panasi.interview_questions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.service.mappers.FullQuestionMapper;
import com.panasi.interview_questions.service.mappers.QuestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	protected final QuestionRepository questionRepository;
	protected final CategoryRepository categoryRepository;
	protected final QuestionMapper questionMapper;
	protected final FullQuestionMapper fullQuestionMapper;
	protected final CommentService commentService;
	
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
