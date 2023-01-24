package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.QuestionComment;

@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Integer> {
	public List<QuestionComment> findAllByQuestionId(int questionId);
	public List<QuestionComment> findAllByAuthorId(int authorId);
	@Query("SELECT AVG(rate) FROM QuestionComment c WHERE c.questionId = ?1")
	public double getRating(int questionId);
}
