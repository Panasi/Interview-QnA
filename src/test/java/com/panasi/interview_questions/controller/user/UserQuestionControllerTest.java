package com.panasi.interview_questions.controller.user;

import static org.hamcrest.CoreMatchers.is;
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
public class UserQuestionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	@WithUserDetails("User1")
	public void showAllQuestions_then_Status403() throws Exception {

	    mvc.perform(get("/admin/questions/all")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isForbidden());
	    
	}
	
	@Test
	@WithUserDetails("User2")
	public void showQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	    	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Admin public question")))
			.andExpect(jsonPath("$[1].name", is("User1 private question")))
			.andExpect(jsonPath("$[2].name", is("User2 public question")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
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
	@WithUserDetails("User1")
	public void showUserQuestionsToFirstUser_then_Status200() throws Exception {
		
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
			.andExpect(jsonPath("$[1].name", is("User1 private question")));
		
		mvc.perform(get("/questions/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("User2")
	public void showUserQuestionsToSecondUser_then_Status200() throws Exception {
		
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
			.andExpect(jsonPath("$[1].name", is("User2 private question")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionById_then_Status200() throws Exception {

	    mvc.perform(get("/questions/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("name", is("Admin public question")))
	      	.andExpect(jsonPath("categoryId", is(1)));
	    
	    mvc.perform(get("/questions/4")
		    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk())
		    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("name", is("User1 private question")))
		    .andExpect(jsonPath("categoryId", is(1)));
	    
	    mvc.perform(get("/questions/6")
		    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isForbidden())
		    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionById_then_Status404() throws Exception {

	    mvc.perform(get("/questions/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element is not found")));
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionById_then_Status400() throws Exception {

	    mvc.perform(get("/questions/wtf")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isBadRequest())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
	    
	}
	
													// Post
	
	@Test
	@WithUserDetails("User1")
	public void addNewQuestion_then_Status201() throws Exception {

		mvc.perform(post("/questions")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"RandomQuestion\","
					+ "\"categoryId\": 1,"
					+ "\"isPrivate\": true }")
			.characterEncoding("utf-8"))
			.andExpect(status().isCreated());
		
	}
	
													// Put
	
	@Test
	@WithUserDetails("User1")
	public void updateQuestion_then_Status202() throws Exception {
			
		mvc.perform(put("/questions/8")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"User1 updated question\"}"))
			.andExpect(status().isAccepted());
				    
		mvc.perform(get("/questions/8")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("User1 updated question")))
			.andExpect(jsonPath("isPrivate", is(true)));
			
	}
	
	@Test
	@WithUserDetails("User1")
	public void updateQuestion_then_Status403() throws Exception {
			
		mvc.perform(put("/questions/9")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"User2 updated question\"}"))
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("message", is("You can't update other users questions")));
			
	}
		
	@Test
	@WithUserDetails("User1")
	public void updateQuestion_then_Status404() throws Exception {
			
		mvc.perform(put("/questions/99")
		    .contentType(MediaType.APPLICATION_JSON)
		    .content("{\"name\": \"User1 updated question\"}"))
		    .andExpect(status().isNotFound());

	}

}