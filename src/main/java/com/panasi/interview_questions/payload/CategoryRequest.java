package com.panasi.interview_questions.payload;

import lombok.Data;

@Data
public class CategoryRequest {
	
	private String name;
	private Integer parentId;

}
