package com.panasi.interview_questions.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "content")
	@Size(min = 1, max = 5)
	private String content;
	
	@Column(name = "rate", nullable = false)
	@Min(1)
	@Max(5)
	private Integer rate;
	
	@Column(name = "question_id", nullable = false)
	private Integer questionId;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "user_id")
	private Integer authorId;

}
