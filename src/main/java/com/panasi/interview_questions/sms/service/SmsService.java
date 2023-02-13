package com.panasi.interview_questions.sms.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	
	public static final String ACCOUNT_SID = "ACe6e298c640efe061c4a8f51eaaaac3fe";
    public static final String AUTH_TOKEN  = "a48bd5d6d3b5004409ed610c7f7312aa";
    public static final String TWILIO_NUMBER = "+13149362385";

    public void sendSms(String phoneNumberTo, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber(phoneNumberTo), new PhoneNumber(TWILIO_NUMBER),text).create();
    }

}
