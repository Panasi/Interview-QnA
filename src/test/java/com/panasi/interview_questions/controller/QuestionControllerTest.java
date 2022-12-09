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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.panasi.interview_questions.InterviewQuestionsApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		  classes = InterviewQuestionsApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class QuestionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
												// Get
	
	@Test
	public void showAllQuestions_then_Status200() throws Exception {

	    mvc.perform(get("/questions")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].question", is("What is Java?")))
	      .andExpect(jsonPath("$[1].question", is("What is JVM?")))
	      .andExpect(jsonPath("$[2].question", is("What is Inheritance?")));
	}
	
	@Test
	public void showQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].question", is("What is Java?")))
	      .andExpect(jsonPath("$[1].question", is("What is JVM?")));
	}
	
	@Test
	public void showQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].question", is("What is Java?")))
	      .andExpect(jsonPath("$[1].question", is("What is JVM?")))
	      .andExpect(jsonPath("$[2].question", is("What is Inheritance?")))
	      .andExpect(jsonPath("$[3].question", is("What is ArrayList?")));
	}
	
	@Test
	public void showQuestion_then_Status200() throws Exception {

	    mvc.perform(get("/questions/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("question", is("What is Java?")));
	}
	
	@Test
	public void showQuestion_then_Status404() throws Exception {

	    mvc.perform(get("/questions/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element is not found")));
	}
	
	@Test
	public void showQuestion_then_Status400() throws Exception {

	    mvc.perform(get("/questions/wtf")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
	}
	
													// Post
	
	@Test
	public void addNewQuestion_then_Status201() throws Exception {

		mvc.perform(post("/questions")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("{\"question\": \"RandomQuestion\","
			      		+ "\"category\": {"
			      		+ "\"id\": 6,"
			      		+ "\"name\": \"Hibernate\"}}"))
			      .andExpect(status().isCreated());
	}
	
													// Put
	
	@Test
	public void updateQuestion_then_Status202() throws Exception {
		
	    mvc.perform(put("/questions/5")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"answer\": \"Java Library\"}"))
	      .andExpect(status().isAccepted());
	    
	    mvc.perform(get("/questions/5")
	  	  .contentType(MediaType.APPLICATION_JSON))
	  	  .andExpect(status().isOk())
	  	  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("answer", is("Java Library")));

	}
	
	@Test
	public void updateQuestion_then_Status404() throws Exception {
		
	    mvc.perform(put("/questions/99")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"answer\": \"Java Library\"}"))
	      .andExpect(status().isNotFound());

	}
	
													// Delete
	
	@Test
	public void deleteQuestion_then_Status200() throws Exception {
		
	    mvc.perform(delete("/questions/6")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	    
	    mvc.perform(get("/questions/6")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	public void deleteQuestion_then_Status404() throws Exception {
		
	    mvc.perform(delete("/questions/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element does not exist")));
	      
	    
	}

}
