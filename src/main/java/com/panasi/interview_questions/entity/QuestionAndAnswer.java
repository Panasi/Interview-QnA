package com.panasi.interview_questions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "questions_and_answers")
@Data
public class QuestionAndAnswer {
	
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
