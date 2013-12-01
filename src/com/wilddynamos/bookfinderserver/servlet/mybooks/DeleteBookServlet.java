package com.wilddynamos.bookfinderserver.servlet.mybooks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookfinderserver.service.BookManager;

public class DeleteBookServlet extends HttpServlet {

	private static final long serialVersionUID = -1457751904607494585L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");System.out.println(id);

		BookManager bm = new BookManager();
		if(bm.removeById(Integer.parseInt(id)) > 0)
			response.getWriter().println("1");
		else
			response.getWriter().println("-1");
		bm.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
