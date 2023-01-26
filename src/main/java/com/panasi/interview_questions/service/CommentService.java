package com.panasi.interview_questions.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.panasi.interview_questions.repository.AnswerCommentRepository;
import com.panasi.interview_questions.repository.QuestionCommentRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;
import com.panasi.interview_questions.repository.entity.QuestionComment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final QuestionCommentRepository questionCommentRepository;
	private final AnswerCommentRepository answerCommentRepository;
	

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

}
