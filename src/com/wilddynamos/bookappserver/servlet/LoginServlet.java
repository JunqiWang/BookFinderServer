package com.wilddynamos.bookappserver.servlet;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3882829776413408269L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserManager um = new UserManager();
		List<User> users = um.findByProp("email", email, null, null, 1, 1);
		um.close();
		
		if(users == null || users.size() == 0) {
			response.getWriter().println("-1");
			return;
		} else if(!users.get(0).getPassword().equals(password)) {
			response.getWriter().println("-1");
			return;
		}
		
		String sessionID = request.getSession(true).getId();
		
		ActiveUserPool.session2user.put(sessionID, users.get(0));
		
		response.getWriter().println(sessionID);
		response.getWriter().println(users.get(0).getId());
	}
}

