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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class GuestAnswerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	public void showAllAnswers_then_Status401() throws Exception {
	
		mvc.perform(get("/admin/answers/all")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void showAllPublicAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/answers")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin public answer")))
			.andExpect(jsonPath("$[1].name", is("User1 public answer")))
			.andExpect(jsonPath("$[2].name", is("User2 public answer")))
			.andExpect(jsonPath("$[3].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	public void showUserAnswers_then_Status200() throws Exception {
		
		mvc.perform(get("/answers/user/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Admin public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
			
		
		mvc.perform(get("/answers/user/2")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User1 public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
		mvc.perform(get("/answers/user/3")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("User2 public answer")))
			.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
		
	}
	
	@Test
	public void showAnswerById_then_Status200() throws Exception {
		
		mvc.perform(get("/answers/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name", is("Admin public answer")))
			.andExpect(jsonPath("questionId", is(1)));
		
	}
	
	@Test
	public void showAnswerById_then_Status403() throws Exception {
		
		mvc.perform(get("/answers/2")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name").doesNotHaveJsonPath());
		
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
	public void addNewAnswer_then_Status401() throws Exception {
			
		mvc.perform(post("/answers")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"RandomAnswer\","
				    + "\"questionId\": 1,"
					+ "\"isPrivate\": true }")
			.characterEncoding("utf-8"))
			.andExpect(status().isUnauthorized());
			
	}
	
													// Put
	
	@Test
	public void updateAnswer_then_Status401() throws Exception {
			
		mvc.perform(put("/answers/8")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\": \"User1 updated answer\"}"))
			.andExpect(status().isUnauthorized());
			
	}
	
													// Delete
	
	@Test
	public void deleteAnswer_then_Status401() throws Exception {

		mvc.perform(delete("/admin/answers/10")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());

	}

}
