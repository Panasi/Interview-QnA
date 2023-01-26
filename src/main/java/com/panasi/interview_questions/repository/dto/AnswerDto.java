package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {
	
	private Integer id;
	private String name;
	private Integer questionId;
	private String authorName;
	private Integer authorId;
	private LocalDateTime date;
	private Boolean isPrivate;
	private Double rating;
	
}
