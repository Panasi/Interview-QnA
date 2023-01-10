package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	public List<Comment> findAllByAnswerId(int answerId);
	@Query("SELECT AVG(rate) FROM Comment WHERE answer_id = answerId")
	public double getRating(@Param("answerId")int answerId);
}
