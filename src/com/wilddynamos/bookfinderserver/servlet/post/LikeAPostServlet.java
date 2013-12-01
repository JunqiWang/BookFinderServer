package com.wilddynamos.bookfinderserver.servlet.post;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookfinderserver.model.Book;
import com.wilddynamos.bookfinderserver.service.BookManager;

public class LikeAPostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7940329382634570899L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		if(id == null)
			return;
		
		BookManager bm = new BookManager();
		
		List<Book> books = bm.findByProp("id", id, null, null, null, 1);
		if(books == null || books.size() == 0)
			return;
		books.get(0).setLikes(books.get(0).getLikes() + 1);
		
		if(bm.update(books.get(0)) != -1)
			response.getWriter().print("1");
		else
			response.getWriter().print("-1");	
		
		bm.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
