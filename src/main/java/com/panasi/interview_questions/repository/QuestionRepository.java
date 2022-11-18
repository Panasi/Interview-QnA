package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	public List<Question> findAllByCategoryId(int id);
}
