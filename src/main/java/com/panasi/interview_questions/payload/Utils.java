package com.panasi.interview_questions.payload;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.panasi.interview_questions.security.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Utils {
	
	// Get current user id
	public static int getCurrentUserId() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = userDetails.getId();
		return userId;
	}
	
	// Get current user name
	public static String getCurrentUserName() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		return userName;
	}

}
