package com.panasi.interview_questions.payload;

import lombok.Data;

@Data
public class QuestionRequest {
	
	private String name;
	private Integer categoryId;
	private Boolean isPrivate;
	
	public QuestionRequest(String name, Integer questionId) {
		this.name = name;
		this.categoryId = questionId;
		this.isPrivate = false;
	}

}
