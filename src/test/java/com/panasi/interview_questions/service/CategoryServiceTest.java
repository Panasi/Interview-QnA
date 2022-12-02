package com.panasi.interview_questions.service;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.panasi.interview_questions.repository.CategoryRepository;
import com.panasi.interview_questions.repository.entity.Category;
import com.panasi.interview_questions.service.mappers.CategoryMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CategoryServiceTest {
	
	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	@Mock
	private CategoryMapperImpl mapper;
	
	
	@Test
	public void getAllCategories() throws Exception {
		
		List<Category> categories = new ArrayList<>();
		Category java = new Category(1, null, "java");
		Category spring = new Category(2, null, "spring");
		categories.add(java);
		categories.add(spring);

		when(repository.findAll()).thenReturn(categories);
        assertThat(service.getAllCategories()).hasSize(2);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
	}

}
