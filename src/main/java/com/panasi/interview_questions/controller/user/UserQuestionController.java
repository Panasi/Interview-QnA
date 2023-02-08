package com.panasi.interview_questions.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.payload.QuestionRequest;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.service.user.UserQuestionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class UserQuestionController {
	
	private final UserQuestionService service;
	
	
	@GetMapping("/category/{categoryId}")
	@Operation(summary = "Get questions from certain category")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromCategory(@PathVariable int categoryId, @RequestParam(defaultValue = "all") String access) {
		List<QuestionDto> allQuestionDtos = service.getCategoryQuestions(categoryId, access);
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/subcategory/{categoryId}")
	@Operation(summary = "Get questions from certain category and all its subcategories")
	public ResponseEntity<List<QuestionDto>> showQuestionsFromSubcategories(@PathVariable int categoryId, @RequestParam(defaultValue = "all") String access) {
		List<QuestionDto> result = new ArrayList<>();
		List<QuestionDto> allSubQuestionDtos = service.getSubcategoriesQuestions(categoryId, access, result);
		return new ResponseEntity<>(allSubQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/user/{authorId}")
	@Operation(summary = "Get user questions")
	public ResponseEntity<?> showAllPrivateQuestions(@PathVariable int authorId, @RequestParam(defaultValue = "all") String access) {
		List<QuestionDto> allQuestionDtos = service.getUserQuestions(authorId, access);
		if (allQuestionDtos == null) {
			String message = "You don't have access to user private questions";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(allQuestionDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a full question with answers and comments by id")
	public ResponseEntity<?> showQuestionById(@PathVariable int id) {
		FullQuestionDto questionDto = service.getQuestionById(id);
		if (questionDto == null) {
			String message = "Question " + id + " is private";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(questionDto, HttpStatus.OK);
	}
	
	@GetMapping("/download")
	@Operation(summary = "Download a PDF file with all questions and answers")
	public ResponseEntity<InputStreamResource> downloadPdf() throws IOException {
		service.createPDF();
		File filePDF = new File("./src/main/resources/temp/Questions And Answers.pdf");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(filePDF));
	    HttpHeaders header = new HttpHeaders();
	    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePDF.getName());
	    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    header.add("Pragma", "no-cache");
	    header.add("Expires", "0");
	    return ResponseEntity.ok()
	        .headers(header)
	        .contentLength(filePDF.length())
	        .contentType(MediaType.parseMediaType("application/octet-stream"))
	        .body(resource);
	}
	
	@GetMapping("/category/{categoryId}/download")
	@Operation(summary = "Download a PDF file with all questions and answers")
	public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable int categoryId) throws IOException {
		service.createPDF(categoryId);
		File filePDF = new File("./src/main/resources/temp/Questions And Answers.pdf");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(filePDF));
	    HttpHeaders header = new HttpHeaders();
	    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePDF.getName());
	    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    header.add("Pragma", "no-cache");
	    header.add("Expires", "0");
	    return ResponseEntity.ok()
	        .headers(header)
	        .contentLength(filePDF.length())
	        .contentType(MediaType.parseMediaType("application/octet-stream"))
	        .body(resource);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add a new question")
	public ResponseEntity<QuestionRequest> addNewQuestion(@RequestBody QuestionRequest questionRequest) {
		service.createQuestion(questionRequest);
		return new ResponseEntity<>(questionRequest, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Update question")
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionRequest questionRequest, @PathVariable int id) {
		boolean result = service.updateQuestion(questionRequest, id);
		if (result) {
			return new ResponseEntity<>(questionRequest, HttpStatus.ACCEPTED);
		}
		String message = "You can't update other users questions";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.FORBIDDEN);
	}

}
