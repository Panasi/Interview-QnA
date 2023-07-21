package com.panasi.interview_questions.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panasi.interview_questions.security.payload.ERole;
import com.panasi.interview_questions.security.repository.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByName(ERole name);

}
