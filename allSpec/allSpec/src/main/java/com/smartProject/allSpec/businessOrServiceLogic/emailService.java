package com.smartProject.allSpec.businessOrServiceLogic;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class emailService {

	public boolean sendmail(String subject,String message,String to) {
		boolean f=false;
		String from="sahilkumarhero1090@gmail.com";
		String host="smtp.gmail.com";
		Properties properties=System.getProperties();
		System.out.println("properties"+properties);
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		javax.mail.Session session=javax.mail.Session.getInstance(properties,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("sahilkumarhero1090@gmail.com","kovn qhrd zeba maaf");
			}
		});
		session.setDebug(true);
		MimeMessage m=new MimeMessage(session);
		try {
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject(subject);
			m.setContent(message,"text/html");
			Transport.send(m);
			System.out.println("send sucess");
			f=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
