package com.panasi.interview_questions.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.panasi.interview_questions.repository.dto.CommentDto;
import com.panasi.interview_questions.repository.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	CommentDto toCommentDto(Comment comment);
	List<CommentDto> toCommentDtos(List<Comment> comments);
	Comment toComment(CommentDto commentDto);

}
