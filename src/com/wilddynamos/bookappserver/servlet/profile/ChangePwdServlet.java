package com.wilddynamos.bookappserver.servlet.profile;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class ChangePwdServlet extends HttpServlet {

	private static final long serialVersionUID = 1689720988163942636L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		UserManager um = new UserManager();
		List<User> users = um.findByProp("id", id, null, null, null, 1);

		if (users.size() == 0) {
			um.close();
			response.getWriter().println("-2");
			return;
		}

		String oldPwd = request.getParameter("oldPassword");
		if(!users.get(0).getPassword().equals(oldPwd)) {
			um.close();
			response.getWriter().println("-1");
			return;
		}
		
		String newPwd = request.getParameter("newPassword");
		users.get(0).setPassword(newPwd);
		if(um.update(users.get(0)) < 1) {
			um.close();
			response.getWriter().println("-2");
			return;
		}
		
		um.close();
		response.getWriter().println("1");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
