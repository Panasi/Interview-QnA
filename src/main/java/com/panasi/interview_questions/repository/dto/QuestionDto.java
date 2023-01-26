package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDto {
	
	private Integer id;
	private String name;
	private Integer categoryId;
	private String authorName;
	private Integer authorId;
	private LocalDateTime date;
	private Boolean isPrivate;
	private Double rating;

}
