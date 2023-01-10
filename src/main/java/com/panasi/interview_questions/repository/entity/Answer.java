package com.panasi.interview_questions.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "question_id", nullable = false)
	private Integer questionId;
	
	@Column(name = "user_name")
	private String authorName;
	
	@Column(name = "user_id")
	private Integer authorId;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "is_private")
	private Boolean isPrivate;
	
	@OneToMany
	@JoinColumn(name = "answer_id")
	private List<Comment> comments;

}
