package com.panasi.interview_questions.repository.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {
	
	private Integer id;
	private String name;
	private List<AnswerDto> answers;
	private CategoryDto category;

}
