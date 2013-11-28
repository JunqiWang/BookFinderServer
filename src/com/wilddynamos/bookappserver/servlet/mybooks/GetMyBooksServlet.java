package com.wilddynamos.bookappserver.servlet.mybooks;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.RequestManager;

public class GetMyBooksServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4953808045786008751L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String id = request.getParameter("id");
		
		//get my books for rent/sell
		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("owner_id", id, null, null, Integer.MAX_VALUE, 1);
		
		JSONArray json = new JSONArray();
		
		for(Book b: books) {
			JSONObject jo = new JSONObject();
			
			jo.put("type", 1);
			jo.put("id", b.getId());
			jo.put("name", b.getName());
			jo.put("sOrR", b.getsOrR());
			jo.put("status", b.getStatus());
			json.add(jo);
		}
		
		//get my requesting/requested books
		RequestManager rm = new RequestManager();
		List<Request> requesters = rm.findByProp("requester_id", id, null, null, null, 1);
		rm.close();
		
		for(Request r: requesters) {
			int bookId = r.getBookId();
			
			books = bm.findByProp("id", String.valueOf(bookId), null, null, Integer.MAX_VALUE, 1);
			
			for(Book b: books) {
				JSONObject jo = new JSONObject();
				
				jo.put("type", 0);
				jo.put("id", b.getId());
				jo.put("name", b.getName());
				jo.put("sOrR", b.getsOrR());
				jo.put("status", b.getStatus());
				json.add(jo);
			}
		}
		
		bm.close();
		System.out.println(json.size());
		System.out.println(json);
		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
