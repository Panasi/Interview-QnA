package com.panasi.interview_questions.security.payload;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest {
	
	@NotBlank
	@Size(min = 5,  max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 20)
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 5,  max = 20)
	private String password;
	
	private Set<String> roles;
	
	

}
