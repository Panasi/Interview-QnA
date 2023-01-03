package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AnswerDto {
	
	private Integer id;
	private String name;
	private Integer questionId;
	private String authorName;
	private Integer authorId;
	private LocalDateTime date;
	private Boolean isPrivate;
	
}
