package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;

@Mapper(componentModel = "spring")
public interface AnswerCommentMapper {
	
	AnswerCommentDto toCommentDto(AnswerComment comment);
	List<AnswerCommentDto> toCommentDtos(List<AnswerComment> comments);
	AnswerComment toComment(AnswerCommentDto commentDto);

}
