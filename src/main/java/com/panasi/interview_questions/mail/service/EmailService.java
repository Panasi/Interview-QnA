package com.panasi.interview_questions.mail.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.panasi.interview_questions.mail.entity.EmailDetails;
import com.panasi.interview_questions.security.payload.ERole;
import com.panasi.interview_questions.security.repository.UserRepository;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	
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
        	logger.error("Error while sending mail", e);
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
        	logger.error("Error while sending mail", e);
            return false;
        }
		
	}
	
	@Scheduled(fixedDelay = 1000 * 60 * 24)
	public void sendReminderEmail() {
		
		List<String> inactiveUserEmails = userRepository.findUserEmailsByRoleAndNotInQuestionList(ERole.ROLE_USER);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("noreply@questionsandanswers.com");
		mailMessage.setText("You haven't ask any questions yet. Try it now!");
		mailMessage.setSubject("Reminder!");
		inactiveUserEmails.forEach(email -> {
			mailMessage.setTo(email);
			try {
				javaMailSender.send(mailMessage);
			} catch (Exception e) {
				logger.error("Error while sending mail", e);
			}
		});
		
	}

}
