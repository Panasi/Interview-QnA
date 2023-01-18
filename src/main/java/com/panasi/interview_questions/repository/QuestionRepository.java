package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	public List<Question> findAllByCategoryId(int categoryId);
	public List<Question> findAllByIsPrivate(Boolean isPrivate);
	public List<Question> findAllByAuthorId(int authorId);
	public List<Question> findAllByAuthorIdAndIsPrivate(int authorId, Boolean isPrivate);
	@Query("SELECT q FROM Question q WHERE (q.categoryId = ?1 AND q.isPrivate = false) OR (q.categoryId = ?1 AND q.isPrivate = true AND q.authorId = ?2)")
	public List<Question> findAllByCategoryIdAndUserId(int categoryId, int userId);
}
