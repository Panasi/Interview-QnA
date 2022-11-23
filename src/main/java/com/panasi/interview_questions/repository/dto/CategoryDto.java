package com.panasi.interview_questions.repository.dto;

import lombok.Data;

@Data
public class CategoryDto {
	
	private int id;
	private int parentId;
	private String name;

}
