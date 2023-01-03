package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {
	
	private Integer id;
	private String name;
	private Integer categoryId;
	private List<AnswerDto> answers;
	private String authorName;
	private Integer authorId;
	private LocalDateTime date;
	private Boolean isPrivate;

}
