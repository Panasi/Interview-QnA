package com.panasi.interview_questions.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.QuestionCommentRepository;
import com.panasi.interview_questions.repository.QuestionRepository;
import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.service.mappers.AnswerCommentMapper;
import com.panasi.interview_questions.service.mappers.QuestionCommentMapper;

@Service
public class CommentService {
	
	@Autowired
	protected QuestionCommentRepository questionCommentRepository;
	@Autowired
	protected AnswerCommentRepository answerCommentRepository;
	@Autowired
	protected QuestionRepository questionRepository;
	@Autowired
	protected AnswerRepository answerRepository;
	@Autowired
	protected QuestionCommentMapper questionCommentMapper;
	@Autowired
	protected AnswerCommentMapper answerCommentMapper;
	
	// Sort question comments
	public List<QuestionCommentDto> sortQuestionCommentDtos(List<QuestionCommentDto> questionCommentDtos) {
		List<QuestionCommentDto> sortedComments = new ArrayList<>(questionCommentDtos);
		sortedComments.sort((q1,q2) -> {
			int compare = q1.getQuestionId().compareTo(q2.getQuestionId());
			if (compare == 0) {
				compare = q1.getDate().compareTo(q2.getDate());
			}
			return compare;
			});
		return sortedComments;
	}
	
	// Sort answer comments
	public List<AnswerCommentDto> sortAnswerCommentDtos(List<AnswerCommentDto> answerCommentDtos) {
		List<AnswerCommentDto> sortedComments = new ArrayList<>(answerCommentDtos);
		sortedComments.sort((q1,q2) -> {
			int compare = q1.getAnswerId().compareTo(q2.getAnswerId());
			if (compare == 0) {
				compare = q1.getDate().compareTo(q2.getDate());
			}
			return compare;
		});
		return sortedComments;
	}

}
