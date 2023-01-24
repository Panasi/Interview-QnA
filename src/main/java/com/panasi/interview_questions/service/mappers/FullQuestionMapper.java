package com.panasi.interview_questions.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.entity.Question;

@Mapper(componentModel = "spring", uses = {FullAnswerMapper.class, QuestionCommentMapper.class})
public interface FullQuestionMapper {
	
	@Mapping(target = "rating", ignore = true)
	FullQuestionDto toFullQuestionDto(Question question);

}
