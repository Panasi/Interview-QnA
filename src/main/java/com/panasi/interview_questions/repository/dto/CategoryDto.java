package com.panasi.interview_questions.repository.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDto {
	
	private Integer id;
	private String name;
	private Integer parentId;
	private List<QuestionDto> questions;

}
