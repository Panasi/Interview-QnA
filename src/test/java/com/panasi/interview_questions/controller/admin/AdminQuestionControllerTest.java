package com.panasi.interview_questions.controller.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class AdminQuestionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	@WithUserDetails("Admin")
	public void showAllQuestions_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions")
	    	.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin public question")))
			.andExpect(jsonPath("$[1].name", is("User1 private question")))
			.andExpect(jsonPath("$[2].name", is("User2 public question")))
			.andExpect(jsonPath("$[3].name", is("Admin private question")))
			.andExpect(jsonPath("$[4].name", is("User1 public question")))
			.andExpect(jsonPath("$[5].name", is("User2 private question")))
			.andExpect(jsonPath("$[6].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllPublicQuestions_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions?access=public")
	    	.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin public question")))
			.andExpect(jsonPath("$[1].name", is("User2 public question")))
			.andExpect(jsonPath("$[2].name", is("User1 public question")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllPrivateQuestions_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions?access=private")
	    	.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].name", is("Admin private question")))
			.andExpect(jsonPath("$[2].name", is("User2 private question")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/category/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	    	.andExpect(jsonPath("$[1].name", is("User1 private question")))
	    	.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPublicQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/category/1?access=public")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	    	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPrivateQuestionsFromCategory_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/category/1?access=private")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$[0].name", is("User1 private question")))
	    	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/subcategory/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Admin public question")))
	      	.andExpect(jsonPath("$[0].rating", is(1.5)))
			.andExpect(jsonPath("$[1].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].rating", is(5.0)))
			.andExpect(jsonPath("$[2].name", is("User2 public question")))
			.andExpect(jsonPath("$[2].rating", is(nullValue())))
			.andExpect(jsonPath("$[3].name", is("Admin private question")))
			.andExpect(jsonPath("$[3].rating", is(3.5)))
			.andExpect(jsonPath("$[4].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPublicQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/subcategory/1?access=public")
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
	@WithUserDetails("Admin")
	public void showPrivateQuestionsFromSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/subcategory/1?access=private")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[0].rating", is(5.0)))
			.andExpect(jsonPath("$[1].name", is("Admin private question")))
			.andExpect(jsonPath("$[1].rating", is(3.5)))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllUserQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/questions/user/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin public question")))
		  	.andExpect(jsonPath("$[1].name", is("Admin private question")))
		  	.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/questions/user/2")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].name", is("User1 public question")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
			
		
		mvc.perform(get("/admin/questions/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name", is("User2 private question")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showUserPublicQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/questions/user/1?access=public")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin public question")))
		  	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/questions/user/2?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
			
		
		mvc.perform(get("/admin/questions/user/3?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showUserPrivateQuestions_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/questions/user/1?access=private")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("$[0].name", is("Admin private question")))
		  	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/questions/user/2?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
			
		
		mvc.perform(get("/admin/questions/user/3?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 private question")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showQuestionById_then_Status200() throws Exception {

	    mvc.perform(get("/admin/questions/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("name", is("Admin public question")))
	      	.andExpect(jsonPath("categoryId", is(1)))
	      	.andExpect(jsonPath("rating", is(1.5)));
	    
	    mvc.perform(get("/admin/questions/3")
		    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk())
		    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("name", is("User1 public question")))
		    .andExpect(jsonPath("categoryId", is(3)))
		    .andExpect(jsonPath("rating", is(nullValue())));
	    
	    mvc.perform(get("/admin/questions/6")
		    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk())
		    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("name", is("User2 private question")))
		    .andExpect(jsonPath("categoryId", is(3)))
		    .andExpect(jsonPath("rating", is(5.0)));
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showQuestionById_then_Status404() throws Exception {

	    mvc.perform(get("/admin/questions/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element is not found")));
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showQuestionById_then_Status400() throws Exception {

	    mvc.perform(get("/admin/questions/wtf")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isBadRequest())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
	    
	}
	
													// Post
	
	@Test
	@WithUserDetails("Admin")
	public void addNewQuestion_then_Status201() throws Exception {

		mvc.perform(post("/admin/questions")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"RandomQuestion\","
				    + "\"categoryId\": 3,"
					+ "\"isPrivate\": true }")
			.characterEncoding("utf-8"))
			.andExpect(status().isCreated());
		
	}
	
													// Put
	
	@Test
	@WithUserDetails("Admin")
	public void updateQuestion_then_Status202() throws Exception {
		
		mvc.perform(put("/admin/questions/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"Admin updated question\"}"))
			.andExpect(status().isAccepted());
				    
		mvc.perform(get("/admin/questions/6")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("Admin updated question")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void updateQuestion_then_Status404() throws Exception {
		
		mvc.perform(put("/admin/questions/99")
		    .contentType(MediaType.APPLICATION_JSON)
		    .content("{\"name\": \"Admin updated question\"}"))
		    .andExpect(status().isNotFound());

	}
	
													// Delete
	
	@Test
	@WithUserDetails("Admin")
	public void deleteQuestion_then_Status200() throws Exception {
		
	    mvc.perform(delete("/admin/questions/6")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isOk());
	    
	    mvc.perform(get("/admin/questions/6")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isNotFound())
	     	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	     	.andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void deleteQuestion_then_Status404() throws Exception {
		
	    mvc.perform(delete("/admin/questions/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isNotFound())
	     	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	     	.andExpect(jsonPath("message", is("Element does not exist")));
	    
	}

}
