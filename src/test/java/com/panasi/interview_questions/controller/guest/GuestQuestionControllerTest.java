package com.panasi.interview_questions.controller.guest;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class GuestQuestionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	public void showAllQuestions_then_Status401() throws Exception {

	    mvc.perform(get("/admin/questions/all")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isUnauthorized());
	    
	}
	
	@Test
	public void showQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	    	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	public void showQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Admin public question")))
			.andExpect(jsonPath("$[1].name", is("User2 public question")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	public void showPublicQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/public")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin public question")))
		  	.andExpect(jsonPath("$[1].name", is("User1 public question")))
		  	.andExpect(jsonPath("$[2].name", is("User2 public question")))
		  	.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	public void showUserQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/user/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin public question")))
		  	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/questions/user/2")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/questions/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	public void showQuestionById_then_Status200() throws Exception {

	    mvc.perform(get("/questions/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("name", is("Admin public question")))
	      	.andExpect(jsonPath("categoryId", is(1)));
	    
	}
	
	@Test
	public void showQuestionById_then_Status403() throws Exception {

	    mvc.perform(get("/questions/2")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isForbidden())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	public void showQuestionById_then_Status404() throws Exception {

	    mvc.perform(get("/questions/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element is not found")));
	    
	}
	
	@Test
	public void showQuestionById_then_Status400() throws Exception {

	    mvc.perform(get("/questions/wtf")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isBadRequest())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
	    
	}
	
													// Post
	
	@Test
	public void addNewQuestion_then_Status401() throws Exception {

		mvc.perform(post("/questions")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"RandomQuestion\","
					+ "\"categoryId\": 1,"
					+ "\"isPrivate\": true }")
			.characterEncoding("utf-8"))
			.andExpect(status().isUnauthorized());
		
	}
	
													// Put
	
	@Test
	public void updateQuestion_then_Status401() throws Exception {
			
		mvc.perform(put("/questions/8")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"User1 updated question\"}"))
			.andExpect(status().isUnauthorized());
			
	}
	
													// Delete
	
	@Test
	public void deleteQuestion_then_Status401() throws Exception {
			
		mvc.perform(delete("/admin/questions/10")
		    .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
		
	}

}
