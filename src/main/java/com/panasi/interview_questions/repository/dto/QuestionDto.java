package com.panasi.interview_questions.repository.dto;

import com.panasi.interview_questions.repository.entity.Category;

import lombok.Data;

@Data
public class QuestionDto {
	
	private int id;
	private String question;
	private String answer;
	private Category category;

}
