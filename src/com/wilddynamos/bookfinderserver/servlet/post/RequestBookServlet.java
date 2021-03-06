package com.wilddynamos.bookfinderserver.servlet.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookfinderserver.model.Book;
import com.wilddynamos.bookfinderserver.model.Request;
import com.wilddynamos.bookfinderserver.service.BookManager;
import com.wilddynamos.bookfinderserver.service.RequestManager;
import com.wilddynamos.bookfinderserver.servlet.ActiveUserPool;

public class RequestBookServlet extends HttpServlet {

	private static final long serialVersionUID = -9034185523542658411L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		if(id == null)
			return;
		String message = request.getParameter("message");
		
		Request r = new Request(message, new java.util.Date(), id, 
					ActiveUserPool.session2user.get(request.getSession().getId()).getId());
		
		RequestManager rm = new RequestManager();
		int row = rm.add(r);
		rm.close();
		
		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("id", id + "", null, null, 1, 1);
		bm.close();
		int ownerId = books.get(0).getOwnerId();
		
		synchronized (ActiveUserPool.userId2bookIds) {
			if(ActiveUserPool.userId2bookIds.get(ownerId) == null)
				ActiveUserPool.userId2bookIds.put(ownerId, new ArrayList<Integer>());
			ActiveUserPool.userId2bookIds.get(ownerId).add(id);
			
			ActiveUserPool.userId2bookIds.notifyAll();
		}
		
		if(row < 0)
			response.getWriter().println(-1);
		else
			response.getWriter().println(1);
	}

}
