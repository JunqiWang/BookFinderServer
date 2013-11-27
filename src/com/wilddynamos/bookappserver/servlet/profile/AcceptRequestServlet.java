package com.wilddynamos.bookappserver.servlet.profile;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.RequestManager;

public class AcceptRequestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4953808045786008751L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int result = -1;
		String bookId = request.getParameter("bookId");
		String requesterId = request.getParameter("requesterId");
		
		RequestManager rm = new RequestManager();
		List<Request> requesters = rm.findByProp("book_id", bookId, "request_time", 
				null, null, 1);

		for(Request r: requesters) {
			if (r.getId() == Integer.parseInt(requesterId))
				r.setStatus(true);
			else r.setStatus(false);
		}
		
		for(Request r: requesters) {
			result = rm.update(r);
		}
		
		rm.close();
		
		response.getWriter().println(String.valueOf(result));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
