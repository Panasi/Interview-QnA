package com.panasi.interview_questions.controller.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
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
	@WithUserDetails("User2")
	public void showPublicQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1?access=public")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	    	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User2")
	public void showPrivateQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/questions/category/1?access=private")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	      	.andExpect(jsonPath("$[0].rating", is(1.5)))
			.andExpect(jsonPath("$[1].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].rating", is(5.0)))
			.andExpect(jsonPath("$[2].name", is("User2 public question")))
			.andExpect(jsonPath("$[2].rating", is(nullValue())))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showPublicQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1?access=public")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	      	.andExpect(jsonPath("$[0].rating", is(1.5)))
			.andExpect(jsonPath("$[1].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].rating", is(nullValue())))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showPrivateQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/questions/subcategory/1?access=private")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[0].rating", is(5.0)))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("User1")
	public void showAllUserQuestionsToFirstUser_then_Status200() throws Exception {
		
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
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].name", is("User1 public question")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/questions/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showPublicUserQuestionsToFirstUser_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/user/1?access=public")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin public question")))
		  	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/questions/user/2?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/questions/user/3?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showPrivateUserQuestionsToFirstUser_then_Status200() throws Exception {
		
		mvc.perform(get("/questions/user/1?access=private")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isForbidden());
		
		mvc.perform(get("/questions/user/2?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")));
		
		mvc.perform(get("/questions/user/3?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden());
		
	}
	
	@Test
	@WithUserDetails("User2")
	public void showAllUserQuestionsToSecondUser_then_Status200() throws Exception {
		
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
			.andExpect(jsonPath("$[1].name", is("User2 private question")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
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
		    .andExpect(jsonPath("message", is("Question 6 is private")));
	    
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
			
		mvc.perform(put("/questions/4")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"User1 updated question\"}"))
			.andExpect(status().isAccepted());
				    
		mvc.perform(get("/questions/4")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("User1 updated question")))
			.andExpect(jsonPath("isPrivate", is(true)));
			
	}
	
	@Test
	@WithUserDetails("User1")
	public void updateQuestion_then_Status403() throws Exception {
			
		mvc.perform(put("/questions/6")
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
