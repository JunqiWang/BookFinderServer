package com.wilddynamos.bookappserver.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.ActiveUserPool;

public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		ActiveUserPool.userId2response
			.remove(ActiveUserPool.session2user.get(request.getSession(true).getId()).getId());
		
		ActiveUserPool.session2user
			.remove(request.getSession(true).getId());
		
		response.getWriter().println(1);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}

