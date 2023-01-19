package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.QuestionComment;

@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Integer> {
	public List<QuestionComment> findAllByQuestionId(int questionId);
	public List<QuestionComment> findAllByUserId(int authorId);
	@Query("SELECT AVG(rate) FROM QuestionComment WHERE question_id = questionId")
	public double getRating(@Param("questionId")int questionId);
}
