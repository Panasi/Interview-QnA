package com.panasi.interview_questions.controller.admin;

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
public class AdminCategoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	                                         // Get
	
	@Test
	@WithUserDetails("Admin")
	public void showAllCategories_then_Status200() throws Exception {

	    mvc.perform(get("/admin/categories")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Category1")))
	      	.andExpect(jsonPath("$[1].name", is("Category2")))
	      	.andExpect(jsonPath("$[1].parentId", is(1)))
	      	.andExpect(jsonPath("$[2].name", is("Category3")));
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showSubcategories_then_Status200() throws Exception {

	    mvc.perform(get("/admin/categories/1/subcategories")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("$[0].name", is("Category2")))
	      	.andExpect(jsonPath("$[0].parentId", is(1)))
	      	.andExpect(jsonPath("$[1].name").doesNotHaveJsonPath());
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void showCategoryById_then_Status200() throws Exception {

	    mvc.perform(get("/admin/categories/2")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("name", is("Category2")))
	      	.andExpect(jsonPath("parentId", is(1)));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void showCategoryById_then_Status404() throws Exception {

	    mvc.perform(get("/admin/categories/99")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  .andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void showCategoryById_then_Status400() throws Exception {

	    mvc.perform(get("/admin/categories/wtf")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isBadRequest())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("The parameter 'id' of value 'wtf' could not be converted to type 'int'")));

	}
	
											// Post
	
	@Test
	@WithUserDetails("Admin")
	public void addNewCategory_then_Status201() throws Exception {

	    mvc.perform(post("/admin/categories")
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content("{\"name\": \"RandomCategory\"}"))
	      	.andExpect(status().isCreated());

	}
	
											// Put
	
	@Test
	@WithUserDetails("Admin")
	public void updateCategory_then_Status202() throws Exception {
		
	    mvc.perform(put("/admin/categories/4")
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content("{\"name\": \"Category4 updated\"}"))
	      	.andExpect(status().isAccepted());
	    
	    mvc.perform(get("/admin/categories/4")
	    	.contentType(MediaType.APPLICATION_JSON))
	  	  	.andExpect(status().isOk())
	  	  	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	  	  	.andExpect(jsonPath("name", is("Category4 updated")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void updateCategory_then_Status404() throws Exception {
		
	    mvc.perform(put("/admin/categories/99")
	    	.contentType(MediaType.APPLICATION_JSON)
	    	.content("{\"name\": \"PHP Updated\"}"))
	      	.andExpect(status().isNotFound());

	}
	
											// Delete
	
	@Test
	@WithUserDetails("Admin")
	public void deleteCategory_then_Status200() throws Exception {
		
	    mvc.perform(delete("/admin/categories/5")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isOk());
	    
	    mvc.perform(get("/admin/categories/5")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element is not found")));

	}
	
	@Test
	@WithUserDetails("Admin")
	public void deleteCategory_then_Status404() throws Exception {
		
	    mvc.perform(delete("/admin/categories/99")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isNotFound())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element does not exist")));
	    
	}
	
	@Test
	@WithUserDetails("Admin")
	public void deleteCategory_then_Status409() throws Exception {
		
	    mvc.perform(delete("/admin/categories/1")
	    	.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isConflict())
	      	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	      	.andExpect(jsonPath("message", is("Element is binded and can't be deleted")));
	    
	}
	

}
