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
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	                                         // Get
	
	@Test
	public void showAllCategories_then_Status200() throws Exception {

	    mvc.perform(get("/categories")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("Java")))
	      .andExpect(jsonPath("$[1].name", is("Java OOP")))
	      .andExpect(jsonPath("$[1].parentId", is(1)))
	      .andExpect(jsonPath("$[2].name", is("Java Collections")))
	      .andExpect(jsonPath("$[2].parentId", is(1)));
	}
	
	@Test
	public void showSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/categories/1/subcategories")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$[0].name", is("Java OOP")))
	      .andExpect(jsonPath("$[0].parentId", is(1)))
	      .andExpect(jsonPath("$[1].name", is("Java Collections")))
	      .andExpect(jsonPath("$[1].parentId", is(1)));
	}
	
	@Test
	public void showCategoryById_then_Status200() throws Exception {

	    mvc.perform(get("/categories/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("name", is("Java")));

	}
	
	@Test
	public void showCategoryById_then_Status404() throws Exception {

	    mvc.perform(get("/categories/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	public void showCategoryById_then_Status400() throws Exception {

	    mvc.perform(get("/categories/wtf")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));

	}
	
											// Post
	
	@Test
	@WithUserDetails("Panasi")
	public void addNewCategory_then_Status201() throws Exception {

	    mvc.perform(post("/categories")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"RandomCategory\"}"))
	      .andExpect(status().isCreated());

	}
	
											// Put
	
	@Test
	@WithUserDetails("Panasi")
	public void updateCategory_then_Status202() throws Exception {
		
	    mvc.perform(put("/categories/7")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"PHP Updated\"}"))
	      .andExpect(status().isAccepted());
	    
	    mvc.perform(get("/categories/7")
	  	  .contentType(MediaType.APPLICATION_JSON))
	  	  .andExpect(status().isOk())
	  	  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("name", is("PHP Updated")));

	}
	
	@Test
	@WithUserDetails("Panasi")
	public void updateCategory_then_Status404() throws Exception {
		
	    mvc.perform(put("/categories/99")
	      .contentType(MediaType.APPLICATION_JSON)
	      .content("{\"name\": \"PHP Updated\"}"))
	      .andExpect(status().isNotFound());

	}
	
											// Delete
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteCategory_then_Status200() throws Exception {
		
	    mvc.perform(delete("/categories/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	    
	    mvc.perform(get("/categories/4")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));
	    
	    mvc.perform(get("/categories/5")
	  	  .contentType(MediaType.APPLICATION_JSON))
	  	  .andExpect(status().isNotFound())
	  	  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteCategory_then_Status404() throws Exception {
		
	    mvc.perform(delete("/categories/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element does not exist")));
	      
	    
	}
	
	@Test
	@WithUserDetails("Panasi")
	public void deleteCategory_then_Status409() throws Exception {
		
	    mvc.perform(delete("/categories/6")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isConflict())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("message", is("Element is binded and can't be deleted")));
	      
	    
	}
	

}
