package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
	
	AnswerDto toAnswerDto(Answer answer);
	List<AnswerDto> toAnswerDtos(List<Answer> answers);
	Answer toAnswer(AnswerDto answerDto);

}
