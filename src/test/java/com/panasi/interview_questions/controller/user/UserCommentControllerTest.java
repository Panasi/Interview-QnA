package com.panasi.interview_questions.controller.user;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UserCommentControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
													// Get
	
	@Test
	@WithUserDetails("User1")
	public void showAllCommentsToQuestion_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/question/1/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isOk())
	  		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  		.andExpect(jsonPath("$[0].content", is("Comment1")))
	  		.andExpect(jsonPath("$[0].rate", is(1)))
	  		.andExpect(jsonPath("$[1].content", is("Comment2")))
	  		.andExpect(jsonPath("$[1].rate", is(2)))
	  		.andExpect(jsonPath("$[2].content").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showAllCommentsToQuestion_then_Status403() throws Exception {
		
		mvc.perform(get("/comments/question/2/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isForbidden())
	  		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  		.andExpect(jsonPath("message", is("You can't get comments to another user's private question")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showAllCommentsToAnswer_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/answer/1/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isOk())
	  		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  		.andExpect(jsonPath("$[0].content", is("Comment1")))
	  		.andExpect(jsonPath("$[0].rate", is(1)))
	  		.andExpect(jsonPath("$[1].content", is("Comment2")))
	  		.andExpect(jsonPath("$[1].rate", is(2)))
	  		.andExpect(jsonPath("$[2].content").doesNotHaveJsonPath());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showAllCommentsToAnswer_then_Status403() throws Exception {
		
		mvc.perform(get("/comments/answer/2/all")
			.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(status().isForbidden())
	  		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  		.andExpect(jsonPath("message", is("You can't get comments to another user's private answer")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showQuestionCommentById_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("content", is("Comment1")))
		  	.andExpect(jsonPath("rate", is(1)));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showAnswerCommentById_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/answers/comment/1")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("content", is("Comment1")))
		  	.andExpect(jsonPath("rate", is(1)));
		
	}
	
	@Test
	@WithUserDetails("User2")
	public void showPrivateQuestionCommentById_then_Status200() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/7")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("content", is("Comment7")))
		  	.andExpect(jsonPath("rate", is(5)));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showPrivateQuestionCommentById_then_Status403() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/3")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isForbidden())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("message", is("You can't get comment to another's user's private question")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showCommentById_then_Status404() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/99")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isNotFound())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("message", is("Element is not found")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void showCommentById_then_Status400() throws Exception {
		
		mvc.perform(get("/comments/questions/comment/wtf")
			.contentType(MediaType.APPLICATION_JSON))
		  	.andExpect(status().isBadRequest())
		  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));
		
	}
	
													// Post
	
	@Test
	@WithUserDetails("User1")
	public void addQuestionComment_then_Status201() throws Exception {
		
		mvc.perform(post("/comments/question/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment\","
					+ "\"rate\": 5}")
			.characterEncoding("utf-8"))
			.andExpect(status().isCreated());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void addAnswerComment_then_Status201() throws Exception {
		
		mvc.perform(post("/comments/answer/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment\","
					+ "\"rate\": 5}")
			.characterEncoding("utf-8"))
			.andExpect(status().isCreated());
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void addQuestionComment_then_Status403() throws Exception {
		
		mvc.perform(post("/comments/question/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment\","
					+ "\"rate\": 5}")
			.characterEncoding("utf-8"))
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("message", is("You can't comment user's private question")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void addAnswerComment_then_Status403() throws Exception {
		
		mvc.perform(post("/comments/answer/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment\","
					+ "\"rate\": 5}")
			.characterEncoding("utf-8"))
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("message", is("You can't comment user's private answer")));
		
	}
	
													// Put
	
	@Test
	@WithUserDetails("User2")
	public void updateQuestionComment_then_Status202() throws Exception {
		
		mvc.perform(put("/comments/questions/comment/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment Updated\"}"))
			.andExpect(status().isAccepted());
					    
		mvc.perform(get("/comments/questions/comment/6")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("content", is("Random Comment Updated")));
		
	}
	
	@Test
	@WithUserDetails("User2")
	public void updateAnswerComment_then_Status202() throws Exception {
		
		mvc.perform(put("/comments/answers/comment/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment Updated\"}"))
			.andExpect(status().isAccepted());
					    
		mvc.perform(get("/comments/answers/comment/6")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("content", is("Random Comment Updated")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void updateQuestionComment_then_Status403() throws Exception {
		
		mvc.perform(put("/comments/questions/comment/6")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"content\": \"Random Comment Updated\"}"))
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("message", is("You can't update another user's comment")));
		
	}
	
	@Test
	@WithUserDetails("User1")
	public void updateComment_then_Status404() throws Exception {
		
	    mvc.perform(put("/comments/answer/comment/99")
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content("{\"content\": \"Random comment\"}"))
	      	.andExpect(status().isNotFound());

	}
	

}
