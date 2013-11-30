package com.wilddynamos.bookappserver.servlet;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = -6917242827560566627L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		UserManager um = new UserManager();
		List<User> users = um.findByProp("email", email, null, null, null, 1);

		if (users == null || users.size() == 0) {
			User user = new User(email, name, password);
			if (um.add(user) < 0)
				response.getWriter().println("-1");
			else
				response.getWriter().println("1");
		} else
			response.getWriter().println("-2");

		um.close();
	}
}
