package com.wilddynamos.bookappserver.servlet.profile;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class ForgotPwdServlet extends HttpServlet {

	private static final long serialVersionUID = 8325321121883351287L;
	private static Properties props;
	static {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
	}

	private static Session session = Session.getInstance(props, new Authenticator() {
		
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service.bookfinder", "bookfinde");
			}
		});

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		
		UserManager um = new UserManager();
		List<User> users = um.findByProp("email", email, null, null, 1, 1);
		
		if(users == null || users.size() == 0) {
			response.getWriter().println("-1");
			um.close();
			return;
		}
		
		String randomPwd = "hahahaha";
		try {
			sendEmail(email, randomPwd);
		} catch (MessagingException e) {
			response.getWriter().println("-2");
			um.close();
			return;
		}
		
		users.get(0).setPassword(randomPwd);
		um.update(users.get(0));
		um.close();
		
		response.getWriter().println("1");
	}
	
	private void sendEmail(String email, String randomPwd) throws MessagingException, IOException {

		MimeMessage message = null;
		synchronized (session) {
			 message = new MimeMessage(session);
		}
		DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(randomPwd, "text/plain"));   
        message.setSender(new InternetAddress("service.bookfinder@gmail.com", "BookFinder Service"));   
		message.setSubject("Temporary Password of Book Finder");
		message.setDataHandler(dataHandler);
		message.setHeader("Header", "Header");
		message.setSentDate(new Date());
		Address toAddress = new InternetAddress(email);
		message.addRecipient(javax.mail.Message.RecipientType.TO,
				toAddress);
		Transport.send(message);
	}
}

