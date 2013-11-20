package com.wilddynamos.bookappserver.servlet.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		UserManager um = new UserManager();
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String campus = request.getParameter("campus");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		
		User user = new User();
		user.setName(name);
		user.setGender(gender.equals("true"));
		user.setCampus(campus);
		user.setContact(contact);
		user.setAddress(address);
		
		int result = um.update(user);
		if (result > 0)
			response.getWriter().println("1");
		else if (result == 0)
			response.getWriter().println("0");
		else if (result < 0)
			response.getWriter().println("-1");
	}

}
