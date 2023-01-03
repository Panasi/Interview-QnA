package com.panasi.interview_questions.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class AnswerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
    												// Get
	
	@Test
	public void showAllAnswers_then_Status200() throws Exception {
	
		mvc.perform(get("/answers")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$[0].name", is("Java is a programming language")))
		  .andExpect(jsonPath("$[0].questionId", is(1)))
		  .andExpect(jsonPath("$[1].name", is("Java is OOP language")));
		
	}
	
	@Test
	public void showAllAnswersToQuestion_then_Status200() throws Exception {
		
		mvc.perform(get("/answers/question/1")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$[0].name", is("Java is a programming language")))
		  .andExpect(jsonPath("$[0].questionId", is(1)))
		  .andExpect(jsonPath("$[1].name", is("Java is OOP language")));
		
	}
	
	@Test
	public void showAllPublicAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/answers/public")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$[0].name", is("Java is OOP language")))
		  .andExpect(jsonPath("$[0].questionId", is(1)));
		
	}
	
	@Test
	public void showAnswerById_then_Status200() throws Exception {
		
		mvc.perform(get("/answers/1")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("name", is("Java is a programming language")))
	      .andExpect(jsonPath("questionId", is(1)));
		
	}
	
	@Test
	public void showAnswerById_then_Status404() throws Exception {
		
		mvc.perform(get("/answers/99")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isNotFound())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("message", is("Element is not found")));
		
	}
	
	@Test
	public void showAnswerById_then_Status400() throws Exception {
		
		mvc.perform(get("/answers/wtf")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isBadRequest())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
		
	}
	
												// Post

	@Test
	@WithMockUser(roles = "USER", username = "Panasi")
	public void addNewAnswer_then_Status201() throws Exception {
		
		mvc.perform(post("/answers")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"name\": \"RandomAnswer\","
			      	+ "\"questionId\": 1}")
		  .characterEncoding("utf-8"))
		  .andExpect(status().isCreated());
		
	}
	
												// Put
	
	@Test
	@WithMockUser(roles = "USER", username = "Panasi")
	public void updateAnswer_then_Status202() throws Exception {
		
		mvc.perform(put("/answers/3")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"name\": \"Java is not a language\"}"))
		  .andExpect(status().isAccepted());
			    
		mvc.perform(get("/answers/3")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("name", is("Java is not a language")))
		  .andExpect(jsonPath("questionId", is(1)));
		
	}
	
	@Test
	@WithMockUser(roles = "USER", username = "Panasi")
	public void updateAnswer_then_Status404() throws Exception {
		
	    mvc.perform(put("/answers/99")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"Random question\"}"))
	      .andExpect(status().isNotFound());

	}
	
												// Delete
	
	@Test
	@WithMockUser(roles = "USER", username = "Panasi")
	public void deleteAnswer_then_Status200() throws Exception {
		
	    mvc.perform(delete("/answers/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	    
	    mvc.perform(get("/answers/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithMockUser(roles = "USER", username = "Panasi")
	public void deleteAnswer_then_Status404() throws Exception {
		
	    mvc.perform(delete("/answers/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element does not exist")));
	      
	    
	}
	
}
