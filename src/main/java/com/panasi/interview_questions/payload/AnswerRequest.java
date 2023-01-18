package com.panasi.interview_questions.payload;

import lombok.Data;

@Data
public class AnswerRequest {
	
	private String name;
	private Integer questionId;
	private Boolean isPrivate;

}
