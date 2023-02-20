package com.panasi.interview_questions.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.sms.service.SmsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SmsController {
	
	private final SmsService smsService;
	
	@PostMapping("/sendSms")
	public ResponseEntity<MessageResponse> sendEmail(@RequestParam() String phoneNumber, @RequestParam() String text) {
		smsService.sendSms(phoneNumber, text);
		String message = "Sms sent";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

}
