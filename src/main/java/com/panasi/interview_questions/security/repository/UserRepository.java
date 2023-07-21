package com.panasi.interview_questions.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.panasi.interview_questions.security.payload.ERole;
import com.panasi.interview_questions.security.repository.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	@Query("SELECT u.email FROM User u INNER JOIN u.roles r WHERE r.name = ?1 AND u.id NOT IN (SELECT q.authorId FROM Question q)")
	List<String> findUserEmailsByRoleAndNotInQuestionList(ERole role);

}
