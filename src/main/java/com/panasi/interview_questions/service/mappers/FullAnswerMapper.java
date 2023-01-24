package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;

@Mapper(componentModel = "spring", uses = AnswerCommentMapper.class)
public interface FullAnswerMapper {
	
	@Mapping(target = "rating", ignore = true)
	FullAnswerDto toFullAnswerDto(Answer answer);
	List<FullAnswerDto> answerListToFullAnswerDtoList(List<Answer> answers);

}
