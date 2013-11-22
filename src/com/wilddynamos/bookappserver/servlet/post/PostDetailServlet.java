package com.wilddynamos.bookappserver.servlet.post;

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

import com.wilddynamos.bookappserver.ActiveUserPool;
import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.RequestManager;

public class PostDetailServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		if(id == null)
			return;
		
		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("id", id, null, null, null, 1);
		bm.close();
		
		if(books == null || books.size() == 0)
			return;
		
		RequestManager rm = new RequestManager();
		List<Request> requests = rm.findByProp("book_id", id, null, null, null, 1);
		rm.close();
		
		Boolean hasRequested = false;
		for(Request r: requests) {
			if(r.getRequesterId().equals(ActiveUserPool.session2user.get(request.getSession().getId()).getId()))
				hasRequested = true;
		}
		
		String path = this.getServletContext().getRealPath("/book_cover");
		File file = new File(path + "/" + books.get(0).getCoverPath());
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
		String s = new String(bytes, Charset.forName("ISO-8859-1"));
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("id", books.get(0).getId());
		jo.put("name", books.get(0).getName());
		jo.put("price", books.get(0).getPrice());
		jo.put("cover", s);
		
		if(!books.get(0).getsOrR()) {
			jo.put("per", books.get(0).getPer());
			jo.put("availableTime", books.get(0).getAvailableTime());
		}
		
		jo.put("owner", books.get(0).getOwner().getName());
		jo.put("description", books.get(0).getDescription());
		jo.put("likes", books.get(0).getLikes());
		jo.put("sOrR", books.get(0).getsOrR());
		
		jo.put("hasRequested", hasRequested);
		
		json.add(jo);
		
		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
