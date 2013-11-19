package com.wilddynamos.bookappserver.servlet.post;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.ActiveUserPool;
import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.RequestManager;
import com.wilddynamos.bookappserver.servlet.LongConnRegServlet;

public class RequestBookServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		synchronized(ActiveUserPool.sb) {
			ActiveUserPool.sb.setCharAt(0, '2');
			ActiveUserPool.sb.notifyAll();
		}
		
		System.out.println(ActiveUserPool.sb);
//		response.setCharacterEncoding("UTF-8");
//		
//		Integer id = Integer.parseInt(request.getParameter("id"));
//		if(id == null)
//			return;
//		String message = request.getParameter("message");
//		
//		Request r = new Request(message, new java.util.Date(), id, 
//					ActiveUserPool.session2user.get(request.getSession().getId()).getId());
//		
//		RequestManager rm = new RequestManager();
//		int row = rm.add(r);
//		rm.close();
//		
//		ActiveUserPool.userId2response.get(1).getWriter().println("hi");
//		
//		BookManager bm = new BookManager();
//		List<Book> books = bm.findByProp("id", id + "", null, null, 1, 1);
//		bm.close();
//		int ownerId = books.get(0).getOwnerId();
//		if(ActiveUserPool.userId2response.keySet().contains(ownerId))
//			ActiveUserPool.userId2response.get(ownerId).getWriter().println("New Request");
//		
//		for(Entry<String, User> e: ActiveUserPool.session2user.entrySet())
//			System.out.println(e.getKey() + " = " + e.getValue().getId());
//		for(Entry<Integer, HttpServletResponse> e: ActiveUserPool.userId2response.entrySet())
//			System.out.println(e.getKey() + " = " + e.getValue().toString());
//		
//		if(row < 0)
//			response.getWriter().println(-1);
//		else
			response.getWriter().println(1);
	}

}
