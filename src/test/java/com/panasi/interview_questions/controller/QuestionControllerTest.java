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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
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
	      .andExpect(jsonPath("$[0].name", is("What is Java?")))
	      .andExpect(jsonPath("$[1].name", is("What is JVM?")))
	      .andExpect(jsonPath("$[2].name", is("What is Inheritance?")));
	}
	
	@Test
	public void showQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("What is Java?")))
	      .andExpect(jsonPath("$[1].name", is("What is JVM?")));
	}
	
	@Test
	public void showQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("What is Java?")))
	      .andExpect(jsonPath("$[1].name", is("What is JVM?")))
	      .andExpect(jsonPath("$[2].name", is("What is Inheritance?")))
	      .andExpect(jsonPath("$[3].name", is("What is ArrayList?")));
	}
	
	@Test
	public void showPublicQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/public")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("What is Inheritance?")))
		  .andExpect(jsonPath("$[1].name", is("What is ArrayList?")));
		
	}
	
	@Test
	@WithUserDetails("Panasi")
	public void showPrivateQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/private")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("What is Java?")))
		  .andExpect(jsonPath("$[1].name", is("What is JVM?")));
		
	}
	
	@Test
	public void showQuestion_then_Status200() throws Exception {

	    mvc.perform(get("/questions/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("name", is("What is Java?")));
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
	@WithUserDetails("Panasi")
	public void addNewQuestion_then_Status201() throws Exception {

		mvc.perform(post("/questions")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("{\"name\": \"RandomQuestion\", "
					      	+ "\"categoryId\": 6}")
				  .characterEncoding("utf-8"))
			      .andExpect(status().isCreated());
	}
	
													// Put
	
	@Test
	@WithUserDetails("Panasi")
	public void updateQuestion_then_Status202() throws Exception {
		
	    mvc.perform(put("/questions/5")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"What is Hibernate updated\"}"))
	      .andExpect(status().isAccepted());
	    
	    mvc.perform(get("/questions/5")
	  	  .contentType(MediaType.APPLICATION_JSON))
	  	  .andExpect(status().isOk())
	  	  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("name", is("What is Hibernate updated")));

	}
	
	@Test
	@WithUserDetails("Panasi")
	public void updateQuestion_then_Status404() throws Exception {
		
	    mvc.perform(put("/questions/99")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"Random question\"}"))
	      .andExpect(status().isNotFound());

	}
	
													// Delete
	
	@Test
	@WithUserDetails("Panasi")
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
	@WithUserDetails("Panasi")
	public void deleteQuestion_then_Status404() throws Exception {
		
	    mvc.perform(delete("/questions/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element does not exist")));
	      
	    
	}

}
