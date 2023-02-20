package com.panasi.interview_questions.mail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.mail.entity.EmailDetails;
import com.panasi.interview_questions.mail.service.EmailService;
import com.panasi.interview_questions.payload.MessageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class EmailController {
	
	private final EmailService emailService;
	
	@PostMapping("/sendEmail")
	public ResponseEntity<MessageResponse> sendEmail(@RequestBody EmailDetails details) {
        boolean status = emailService.sendEmail(details);
        if (status) {
        	String message = "Mail sent successfully";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
        }
        String message = "Error while sending mail";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.NOT_FOUND);
    }

}
