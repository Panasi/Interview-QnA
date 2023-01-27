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
public class AdminAnswerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
    												// Get
	
	@Test
	@WithUserDetails("Admin")
	public void showAllAnswers_then_Status200() throws Exception {
	
		mvc.perform(get("/admin/answers")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin private answer")))
			.andExpect(jsonPath("$[1].name", is("User1 private answer")))
			.andExpect(jsonPath("$[2].name", is("User2 public answer")))
			.andExpect(jsonPath("$[3].name", is("User1 public answer")))
			.andExpect(jsonPath("$[4].name", is("User2 private answer")))
			.andExpect(jsonPath("$[5].name", is("Admin public answer")))
			.andExpect(jsonPath("$[6].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPublicAnswers_then_Status200() throws Exception {
	
		mvc.perform(get("/admin/answers?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public answer")))
			.andExpect(jsonPath("$[1].name", is("User1 public answer")))
			.andExpect(jsonPath("$[2].name", is("Admin public answer")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPrivateAnswers_then_Status200() throws Exception {
	
		mvc.perform(get("/admin/answers?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin private answer")))
			.andExpect(jsonPath("$[1].name", is("User1 private answer")))
			.andExpect(jsonPath("$[2].name", is("User2 private answer")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAllUserAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/answers/user/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin private answer")))
			.andExpect(jsonPath("$[1].name", is("Admin public answer")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/2")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private answer")))
			.andExpect(jsonPath("$[1].name", is("User1 public answer")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public answer")))
			.andExpect(jsonPath("$[1].name", is("User2 private answer")))
			.andExpect(jsonPath("$[2].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAPublicUserAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/answers/user/1?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/2?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/3?access=public")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showPrivateUserAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/answers/user/1?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin private answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/2?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 private answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/admin/answers/user/3?access=private")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 private answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAnswerById_then_Status200() throws Exception {
		
		mvc.perform(get("/admin/answers/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("Admin public answer")))
			.andExpect(jsonPath("questionId", is(3)))
			.andExpect(jsonPath("rating", is(1.5)));
		
		mvc.perform(get("/admin/answers/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("User1 public answer")))
			.andExpect(jsonPath("questionId", is(2)))
			.andExpect(jsonPath("rating", is(nullValue())));
		
		mvc.perform(get("/admin/answers/6")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("User2 private answer")))
			.andExpect(jsonPath("questionId", is(2)))
			.andExpect(jsonPath("rating", is(5.0)));
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAnswerById_then_Status404() throws Exception {
		
		mvc.perform(get("/admin/answers/99")
			.contentType(MediaType.APPLICATION_JSON))
		 	.andExpect(status().isNotFound())
		 	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		 	.andExpect(jsonPath("message", is("Element is not found")));
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showAnswerById_then_Status400() throws Exception {
		
		mvc.perform(get("/admin/answers/wtf")
			.contentType(MediaType.APPLICATION_JSON))
		 	.andExpect(status().isBadRequest())
		 	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		 	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
		
	}
	
												// Post

	@Test
	@WithUserDetails("Admin")
	public void addNewAnswer_then_Status201() throws Exception {
		
		mvc.perform(post("/admin/answers")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"RandomAnswer\","
				    + "\"questionId\": 1,"
					+ "\"isPrivate\": true }")
			.characterEncoding("utf-8"))
		 	.andExpect(status().isCreated());
		
	}
	
												// Put
	
	@Test
	@WithUserDetails("Admin")
	public void updateAnswer_then_Status202() throws Exception {
		
		mvc.perform(put("/admin/answers/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"Admin updated answer\"}"))
		 	.andExpect(status().isAccepted());
			    
		mvc.perform(get("/admin/answers/6")
			.contentType(MediaType.APPLICATION_JSON))
		 	.andExpect(status().isOk())
		 	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		 	.andExpect(jsonPath("name", is("Admin updated answer")));
		
	}
	
	@Test
	@WithUserDetails("Admin")
	public void updateAnswer_then_Status404() throws Exception {
		
	    mvc.perform(put("/admin/answers/99")
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content("{\"name\": \"Admin updated answer\"}"))
	     	.andExpect(status().isNotFound());

	}
	
												// Delete
	
	@Test
	@WithUserDetails("Admin")
	public void deleteAnswer_then_Status200() throws Exception {
		
	    mvc.perform(delete("/admin/answers/6")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isOk());
	    
	    mvc.perform(get("/admin/answers/6")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isNotFound())
	     	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	     	.andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void deleteAnswer_then_Status404() throws Exception {
		
	    mvc.perform(delete("/admin/answers/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	     	.andExpect(status().isNotFound())
	     	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	     	.andExpect(jsonPath("message", is("Element does not exist")));
	    
	}
	
}
