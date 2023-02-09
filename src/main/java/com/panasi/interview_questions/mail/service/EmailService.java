package com.panasi.interview_questions.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.mail.entity.EmailDetails;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean sendEmail(EmailDetails emailDetails) {
		
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailDetails.getSender());
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }
		
	}
	
	public boolean sendAuthEmail(String recipient) {
		
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("noreply@questionsandanswers.com");
            mailMessage.setTo(recipient);
            mailMessage.setText("You have successfully registered in 'Questions and answers' application.");
            mailMessage.setSubject("Welcome!");
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }
		
	}

}
