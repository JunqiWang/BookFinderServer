package com.wilddynamos.bookappserver.servlet.post;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.servlet.ActiveUserPool;

public class PostListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4953808045786008751L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		if(currentPage == null)
			currentPage = 1;
		
		Boolean sOrR = null;
		if(request.getParameter("sOrR").equals("s"))
			sOrR = true;
		else if(request.getParameter("sOrR").equals("r"))
			sOrR = false;
		
		String search = request.getParameter("search");

		BookManager bm = new BookManager();
		List<Book> books = bm.findAllAvailableBooks(currentPage, null, sOrR, search, 
				ActiveUserPool.session2user.get(request.getSession(true).getId()).getId());
		bm.close();
		
		JSONArray json = new JSONArray();
		for(Book b: books) {
			JSONObject jo = new JSONObject();
			
			jo.put("id", b.getId());
			jo.put("name", b.getName());
			jo.put("price", "$ " + b.getPrice());
			
			if(!b.getsOrR())
				jo.put("per", "/" + (b.getPer() ? "w" : "m"));
			
			jo.put("likes", b.getLikes());
			jo.put("sOrR", b.getsOrR());
			json.add(jo);
		}
		System.out.println(json.size());
		response.getWriter().print(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
