package com.wilddynamos.bookappserver.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -2446419866886728727L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		ActiveUserPool.session2user
			.remove(request.getSession(true).getId());
		
		response.getWriter().println(1);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}

