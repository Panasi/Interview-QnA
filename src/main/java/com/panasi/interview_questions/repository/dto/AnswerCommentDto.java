package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AnswerCommentDto {
	
	private Integer id;
	private String content;
	private Integer rate;
	private Integer answerId;
	private LocalDateTime date;
	private Integer authorId;

}
