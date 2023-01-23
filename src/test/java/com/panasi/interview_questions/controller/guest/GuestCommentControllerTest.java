package com.panasi.interview_questions.controller.guest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class GuestCommentControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	public void showAllCommentsToQuestion_then_Status401() throws Exception {
		
		mvc.perform(get("/comments/question/1/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void showAllCommentsToAnswer_then_Status401() throws Exception {
		
		mvc.perform(get("/comments/answer/1/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void showQuestionCommentById_then_Status401() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void showAnswerCommentById_then_Status401() throws Exception {
		
		mvc.perform(get("/comments/answers/comment/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isUnauthorized());
		
	}
	
													// Post
	
	@Test
	public void addQuestionComment_then_Status401() throws Exception {
		
		mvc.perform(post("/comments/question/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment\","
					+ "\"rate\": 5}")
			.characterEncoding("utf-8"))
			.andExpect(status().isUnauthorized());
		
	}
	
													// Put
	
	@Test
	public void updateQuestionComment_then_Status401() throws Exception {
		
		mvc.perform(put("/comments/questions/comment/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment Updated\"}"))
			.andExpect(status().isUnauthorized());
		
	}
	
													// Delete
	
	@Test
	public void deleteQuestionComment_then_Status401() throws Exception {
			
		mvc.perform(delete("/admin/comments/questions/comment/5")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
	}

}