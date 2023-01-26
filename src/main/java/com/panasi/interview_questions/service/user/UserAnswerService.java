package com.panasi.interview_questions.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.payload.AnswerRequest;
import com.panasi.interview_questions.payload.Utils;
import com.panasi.interview_questions.repository.AnswerRepository;
import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.service.mappers.AnswerMapper;
import com.panasi.interview_questions.service.mappers.FullAnswerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
	
	private final AnswerRepository answerRepository;
	private final AnswerMapper answerMapper;
	private final FullAnswerMapper fullAnswerMapper;
	private final Utils utils;
	
	
	// Return user answers
	public List<AnswerDto> getUserAnswers(int authorId, String access) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Answer> answers;
		if (access.equals("public") || (access.equals("all") && authorId != userDetails.getId())) {
			answers = answerRepository.findAllByAuthorIdAndIsPrivate(authorId, false);
		} else if (access.equals("private") && authorId == userDetails.getId()) {
			answers = answerRepository.findAllByAuthorIdAndIsPrivate(authorId, true);
		} else if (access.equals("private") && authorId != userDetails.getId()) {
	    	return null;
	    } else {
			answers = answerRepository.findAllByAuthorId(authorId);
		}
		List<AnswerDto> answerDtos = answerMapper.toAnswerDtos(answers);
		answerDtos.stream().forEach(answer -> utils.setAnswerRating(answer));
		return answerDtos;
	}
	
	// Return answer by id
	public FullAnswerDto getAnswerById(int answerId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Answer answer = answerRepository.findById(answerId).get();
		if (answer.getIsPrivate() && answer.getAuthorId() != userDetails.getId()) {
			return null;
		}
		FullAnswerDto answerDto = fullAnswerMapper.toFullAnswerDto(answer);
		utils.setAnswerRating(answerDto);
		return answerDto;
	}
	
	// Add a new answer
	public void createAnswer(AnswerRequest answerRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authorName = userDetails.getUsername();
		int authorId = userDetails.getId();
		LocalDateTime dateTime = LocalDateTime.now();
		AnswerDto answerDto = AnswerDto.builder()
			.name(answerRequest.getName())
			.questionId(answerRequest.getQuestionId())
			.isPrivate(answerRequest.getIsPrivate())
			.authorName(authorName)
			.authorId(authorId)
			.date(dateTime)
			.build();
		Answer answer = answerMapper.toAnswer(answerDto);
		answerRepository.save(answer);
	}
	
	// Update certain answer
	public boolean updateAnswer(AnswerRequest answerRequest, int answerId) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Answer answer = answerRepository.findById(answerId).get();
		if (answer.getAuthorId() == userDetails.getId()) {
			LocalDateTime dateTime = LocalDateTime.now();
			answer.setDate(dateTime);
			if (Objects.nonNull(answerRequest.getName())) {
				answer.setName(answerRequest.getName());
			}
			if (Objects.nonNull(answerRequest.getQuestionId())) {
				answer.setQuestionId(answerRequest.getQuestionId());
			}
			if (Objects.nonNull(answerRequest.getIsPrivate())) {
				answer.setIsPrivate(answerRequest.getIsPrivate());
			}
			answerRepository.save(answer);
			return true;
		}
		return false;
	}

}
