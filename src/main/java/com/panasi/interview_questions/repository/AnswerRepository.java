package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	
	public List<Answer> findAllByQuestionId(int questionId);
	
	public List<Answer> findAllByIsPrivate(Boolean isPrivate);
	
	public List<Answer> findAllByAuthorId(int authorId);
	
	public List<Answer> findAllByAuthorIdAndIsPrivate(int authorId, Boolean isPrivate);
	
}
