package com.panasi.interview_questions.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.panasi.interview_questions.SpringSecurityTestConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringSecurityTestConfig.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CommentControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	public void showAllCommentsToQuestion_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/toquestion/1")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$[0].content", is("I dont understand this question.")))
		  .andExpect(jsonPath("$[0].rate", is(3)))
		  .andExpect(jsonPath("$[1].content", is("Stupid question.")))
		  .andExpect(jsonPath("$[1].rate", is(1)));
		
	}
	
	@Test
	public void showAllCommentsToAnswer_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/toanswer/1")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$[0].content", is("This in not a complete answer.")))
		  .andExpect(jsonPath("$[0].rate", is(3)))
		  .andExpect(jsonPath("$[1].content", is("Not bad.")))
		  .andExpect(jsonPath("$[1].rate", is(5)));
		
	}
	
	@Test
	public void showQuestionCommentById_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/question/2")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("content", is("Stupid question.")))
		  .andExpect(jsonPath("rate", is(1)));
		
	}
	
	@Test
	public void showAnswerCommentById_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/answer/2")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("content", is("Not bad.")))
		  .andExpect(jsonPath("rate", is(5)));
		
	}
	
	@Test
	public void showCommentById_then_Status404() throws Exception {
		
		mvc.perform(get("/comments/answer/99")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isNotFound())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("message", is("Element is not found")));
		
	}
	
	@Test
	public void showCommentById_then_Status400() throws Exception {
		
		mvc.perform(get("/comments/answer/wtf")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isBadRequest())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
		
	}
	
													// Post
	
	@Test
	@WithUserDetails("Panasi")
	public void addQuestionComment_then_Status201() throws Exception {
		
		mvc.perform(post("/comments/question")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"content\": \"Random Comment\","
				  	+ "\"rate\": 5,"
			      	+ "\"parentId\": 1}")
		  .characterEncoding("utf-8"))
		  .andExpect(status().isCreated());
		
	}
	
	@Test
	@WithUserDetails("Panasi")
	public void addAnswerComment_then_Status201() throws Exception {
		
		mvc.perform(post("/comments/answer")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"content\": \"Random Comment\","
				  	+ "\"rate\": 5,"
			      	+ "\"parentId\": 1}")
		  .characterEncoding("utf-8"))
		  .andExpect(status().isCreated());
		
	}
	
													// Put
	
	@Test
	@WithUserDetails("Panasi")
	public void updateQuestionComment_then_Status202() throws Exception {
		
		mvc.perform(put("/comments/question/3")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"content\": \"Random Comment Updated\"}"))
		  .andExpect(status().isAccepted());
			    
		mvc.perform(get("/comments/question/3")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("content", is("Random Comment Updated")));
		
	}
	
	@Test
	@WithUserDetails("Panasi")
	public void updateAnswerComment_then_Status202() throws Exception {
		
		mvc.perform(put("/comments/answer/3")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"content\": \"Random Comment Updated\"}"))
		  .andExpect(status().isAccepted());
			    
		mvc.perform(get("/comments/answer/3")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("content", is("Random Comment Updated")));
		
	}
	
	@Test
	@WithUserDetails("Panasi")
	public void updateComment_then_Status404() throws Exception {
		
	    mvc.perform(put("/comments/answer/99")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"content\": \"Random comment\"}"))
	      .andExpect(status().isNotFound());

	}
	
													// Delete
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteQuestionComment_then_Status200() throws Exception {
		
	    mvc.perform(delete("/comments/question/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	    
	    mvc.perform(get("/comments/question/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteAnswerComment_then_Status200() throws Exception {
		
	    mvc.perform(delete("/comments/answer/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	    
	    mvc.perform(get("/comments/answer/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteComment_then_Status404() throws Exception {
		
	    mvc.perform(delete("/comments/answer/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element does not exist")));
	      
	    
	}

}
