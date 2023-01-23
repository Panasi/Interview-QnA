package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;

@Mapper(componentModel = "spring")
public interface FullAnswerMapper {
	
	@Mapping(target = "rating", ignore = true)
	FullAnswerDto toAnswerDto(Answer answer);
	List<FullAnswerDto> toAnswerDtos(List<Answer> answers);
	Answer toFullAnswer(FullAnswerDto fullAnswerDto);

}
