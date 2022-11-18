package com.panasi.interview_questions.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions_and_answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="question", nullable = false)
	private String question;
	
	@Column(name="answer", nullable = true)
	private String answer;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

}
