package com.panasi.interview_questions.repository.dto;

import lombok.Data;

@Data
public class CategoryDto {
	
	private Integer id;
	private Integer parentId;
	private String name;

}
