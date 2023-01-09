package com.panasi.interview_questions.repository.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDto {
	
	private Integer id;
	private String content;
	private Integer rate;
	private Integer answerId;
	private LocalDateTime date;
	private String authorName;
	private Integer authorId;

}
