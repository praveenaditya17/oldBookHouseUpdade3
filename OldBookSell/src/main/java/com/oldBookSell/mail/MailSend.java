package com.oldBookSell.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSend {
	public MailSend() {
		
	}
	@Autowired
    private JavaMailSender sender;
	
	public  String sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("145119pk@gmail.com");
            helper.setText("Greetings :) this is the information for text");
            helper.setText("by raj aditya");
            helper.setSubject("OldBookHouse");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }
}
