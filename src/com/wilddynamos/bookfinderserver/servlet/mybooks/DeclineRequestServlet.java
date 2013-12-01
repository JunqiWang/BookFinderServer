package com.wilddynamos.bookfinderserver.servlet.mybooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookfinderserver.model.Request;
import com.wilddynamos.bookfinderserver.service.RequestManager;
import com.wilddynamos.bookfinderserver.servlet.ActiveUserPool;

public class DeclineRequestServlet extends HttpServlet {
	
	private static final long serialVersionUID = -7094594505620947133L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String bookId = request.getParameter("bookId");
		int requesterId = Integer.parseInt(request.getParameter("requesterId"));
		
		RequestManager rm = new RequestManager();
		List<Request> requests = rm.findByProp("book_id", bookId, "request_time", null, null, 1);

		int result = -1;
		for(Request r: requests)
			if (r.getRequesterId() == requesterId) {
				r.setStatus(false);
				result = rm.update(r);
				break;
			}
		
		rm.close();
		if(result <= 0) {
			response.getWriter().println("-1");
			return;
		}
		
		synchronized (ActiveUserPool.userId2bookIds) {
			for(Request r: requests) {
				if(ActiveUserPool.userId2bookIds.get(r.getRequesterId()) == null)
					ActiveUserPool.userId2bookIds.put(r.getRequesterId(), new ArrayList<Integer>());
				ActiveUserPool.userId2bookIds.get(r.getRequesterId()).add(- Integer.parseInt(bookId));
				System.out.println(ActiveUserPool.userId2bookIds.get(r.getRequesterId()).get(0));
			}
			
			ActiveUserPool.userId2bookIds.notifyAll();
		}
		
		response.getWriter().println("1");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
