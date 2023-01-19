package com.panasi.interview_questions.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentRequest {
	
	@Size(max = 500)
	private String content;
	@Min(1)
	@Max(5)
	private Integer rate;

}
