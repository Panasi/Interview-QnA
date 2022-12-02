package com.panasi.interview_questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class InterviewQuestionsApplicationTest {
	
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void loadContext() {
		Assertions.assertNotNull(applicationContext);
	}

}
