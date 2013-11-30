package com.wilddynamos.bookappserver.servlet.mybooks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.RequestManager;

public class WithdrawRequestServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4192737679041100585L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String bookId = request.getParameter("bookId");
		String requesterId = request.getParameter("requesterId");
		
		RequestManager rm = new RequestManager();
		Request req = rm.findByBookAndRequester(bookId, requesterId);
		int result = rm.removeById(req.getId());
		rm.close();
		
		response.getWriter().println(String.valueOf(result));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
