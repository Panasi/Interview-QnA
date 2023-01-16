package com.panasi.interview_questions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.panasi.interview_questions.security.payload.ERole;
import com.panasi.interview_questions.security.repository.entity.Role;
import com.panasi.interview_questions.security.repository.entity.User;
import com.panasi.interview_questions.security.service.UserDetailsImpl;

@TestConfiguration
public class SpringSecurityTestConfig {
	
	@Bean
    @Primary
    public UserDetailsService userDetailsService() {
		
		Set<Role> roles = new HashSet<>();
		Role adminRole = new Role(1, ERole.ROLE_ADMIN);
		roles.add(adminRole);
		User testUser = new User(1, "Panasi", "email@gmail.com", "password", roles);
		UserDetailsImpl userDetails = UserDetailsImpl.build(testUser);
		return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
	}

}
