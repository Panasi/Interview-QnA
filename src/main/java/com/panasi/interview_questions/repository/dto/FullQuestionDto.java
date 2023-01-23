package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class FullQuestionDto {
	
	private Integer id;
	private String name;
	private Integer categoryId;
	private String authorName;
	private Integer authorId;
	private LocalDateTime date;
	private Boolean isPrivate;
	private Double rating;
	private List<FullAnswerDto> answers;
	private List<QuestionCommentDto> comments;
}
