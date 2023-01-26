package com.panasi.interview_questions.repository.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
	
	private Integer id;
	private String name;
	private Integer parentId;

}
