package com.panasi.interview_questions.repository.dto;

import lombok.Data;

@Data
public class QuestionDto {
	
	private Integer id;
	private String question;
	private String answer;
	private CategoryDto category;

}
