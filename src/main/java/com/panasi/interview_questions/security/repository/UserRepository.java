package com.panasi.interview_questions.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.security.repository.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);

}
