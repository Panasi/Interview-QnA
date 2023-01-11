package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
	
	@Mapping(target = "rating", ignore = true)
	AnswerDto toAnswerDto(Answer answer);
	List<AnswerDto> toAnswerDtos(List<Answer> answers);
	@Mapping(target = "comments", ignore = true)
	Answer toAnswer(AnswerDto answerDto);

}
