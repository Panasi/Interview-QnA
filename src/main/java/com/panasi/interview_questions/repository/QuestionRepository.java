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
	@Query(value = "SELECT * FROM Question WHERE (category_id = categoryid) AND ((is_private = 'false') OR (user_id = AuthoId AND is_private = 'true'))", nativeQuery = true)
	public List<Question> findAllByCategoryIdAndUserId(int categoryid, int AuthoId);
}
