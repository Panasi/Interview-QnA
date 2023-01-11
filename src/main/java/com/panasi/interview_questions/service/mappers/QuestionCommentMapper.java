package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.repository.entity.QuestionComment;

@Mapper(componentModel = "spring")
public interface QuestionCommentMapper {
	
	QuestionCommentDto toCommentDto(QuestionComment comment);
	List<QuestionCommentDto> toCommentDtos(List<QuestionComment> comments);
	QuestionComment toComment(QuestionCommentDto commentDto);

}
