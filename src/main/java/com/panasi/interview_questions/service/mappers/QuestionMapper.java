package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	
	@Mapping (target ="categoryDto", source ="category")
	QuestionDto toQuestionDto(Question question);
	List<QuestionDto> toQuestionDtos(List<Question> questions);
	@Mapping (target ="category", source ="categoryDto")
	Question toQuestion(QuestionDto QuestionDto);

}
