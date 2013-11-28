package com.wilddynamos.bookappserver.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.RequestManager;

public class BookDetailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1563500391606203016L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		if(id == null)
			return;
		
		String isMine = request.getParameter("isMine");
		String isRequested = request.getParameter("isRequested");
		
		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("id", id, null, null, null, 1);
		bm.close();
		
		if(books == null || books.size() == 0)
			return;
		
		//for post only begin
		Boolean hasRequested = null;
		if(isMine == null && isRequested == null) {
			RequestManager rm = new RequestManager();
			List<Request> requests = rm.findByProp("book_id", id, null, null, null, 1);
			rm.close();
			
			hasRequested = false;
			for(Request r: requests)
				if(r.getRequesterId().equals(ActiveUserPool
						.session2user.get(request.getSession().getId()).getId()))
					hasRequested = true;
		}
		//for post only end
		
		//for my post only begin
		int requesterNum = 0;
		Boolean hasMadeRespond = false;
		Integer requesterId = null;
		if(isMine != null) {
			RequestManager rm = new RequestManager();
			List<Request> requests = rm.findByProp("book_id", id, null, null, null, 1);
			rm.close();
			
			for(Request r: requests) {
				if(r.getBookId().equals(Integer.parseInt(id))) {
					if(r.getStatus() != null && r.getStatus()) {
						hasMadeRespond = true;
						requesterId = r.getRequesterId();
						break;
					}
					
					if(r.getStatus() == null)
						requesterNum ++;
				}
			}
		}
		//for my post only end
		
		//for my request only begin
		int hasResponded = 0;
		if(isRequested != null) {
			RequestManager rm = new RequestManager();
			List<Request> requests = rm.findByProp("book_id", id, null, null, null, 1);
			rm.close();
			
			for(Request r: requests) {
				if(r.getRequesterId().equals(ActiveUserPool
						.session2user.get(request.getSession().getId()).getId())
						&& r.getStatus() != null) {
					hasResponded = r.getStatus() ? 1 : -1;
				}
			}
		}
		//for my request only end
		
		//image begin
		String image = null;
		String path = this.getServletContext().getRealPath("/book_cover");
		File file = new File(path + "/" + books.get(0).getCoverPath());
		if(file.exists() && file.isFile()) {
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 1024];
		
			try {
				for(int readNum; (readNum = fis.read(buf)) != -1;)
					bos.write(buf, 0, readNum);
			} catch(Exception e) {
				e.printStackTrace();
			}
			byte[] bytes = bos.toByteArray();
			image = new String(bytes, Charset.forName("ISO-8859-1"));
		}
		//image end
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("id", books.get(0).getId());
		jo.put("name", books.get(0).getName());
		jo.put("price", books.get(0).getPrice());
		jo.put("sOrR", books.get(0).getsOrR());
		if(!books.get(0).getsOrR()) {
			jo.put("per", books.get(0).getPer());
			jo.put("availableTime", books.get(0).getAvailableTime());
		}
		jo.put("cover", image);
		jo.put("description", books.get(0).getDescription());
		jo.put("likes", books.get(0).getLikes());
		
		//not my post begin
		if(isMine == null)
			jo.put("owner", books.get(0).getOwner().getName());
		//not my post end
		
		//is post begin
		if(isMine == null && isRequested == null)
			jo.put("hasRequested", hasRequested);
		//is post end
		
		//is my post begin
		if(isMine != null) {
			//requesters
			jo.put("hasMadeRespond", hasMadeRespond);
			if(hasMadeRespond)
				jo.put("requesterId", requesterId);
			else
				jo.put("requesterNum", requesterNum);
		}
		//is my post end
		
		//is my request begin
		if(isRequested != null)
			jo.put("hasResponded", hasResponded);System.out.println(hasResponded);
		//is my request end
		
		json.add(jo);
		
		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
