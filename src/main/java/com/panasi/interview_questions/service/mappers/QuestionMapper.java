package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	
	QuestionDto toQuestionDto(Question question);
	List<QuestionDto> toQuestionDtos(List<Question> questions);
	@Mapping(target = "answers", ignore = true)
	Question toQuestion(QuestionDto QuestionDto);

}
