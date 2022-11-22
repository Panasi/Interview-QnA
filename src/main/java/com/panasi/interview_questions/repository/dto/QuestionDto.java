package com.panasi.interview_questions.repository.dto;

import lombok.Data;

@Data
public class QuestionDto {
	
	private int id;
	private String question;
	private String answer;
	private CategoryDto categoryDto;

}
