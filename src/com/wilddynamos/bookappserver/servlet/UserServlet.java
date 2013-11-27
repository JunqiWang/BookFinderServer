package com.wilddynamos.bookappserver.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.*;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.UserManager;


public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7700416442172664161L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		UserManager um = new UserManager();
		List<User> users = um.findByProp("email", "wos", null, null, null, 2);
		um.close();
		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("name", "av", null, null, null, 2);
		System.out.println(books.get(0).getName());
		
		JSONArray json = new JSONArray();
		
		if(request.getCookies() == null) {
			JSONObject jo = new JSONObject();
			jo.put("JSESSIONID", request.getSession().getId());
			json.add(jo);
		}
		
		for(User u: users) {
			JSONObject jo = new JSONObject();
			jo.put("id", u.getId());
			jo.put("email", u.getEmail());
			jo.put("password", u.getPassword());
			jo.put("name", u.getName());
			jo.put("gender", u.getGender());
			jo.put("campus", u.getCampus());
			jo.put("contact", u.getContact());
			jo.put("address", u.getAddress());
			jo.put("photoPath", u.getPhotoPath());
			json.add(jo);
		}

		for(Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			System.out.println(name+"="+request.getHeader(name));
			
		}
//		System.out.println(response.getHeaderNames().size());
//		for(Iterator<String> e = response.getHeaderNames().iterator(); e.hasNext();)
//			System.out.println(e.next());
//		System.out.println(request.getCookies()[0].getName() +"="+request.getCookies()[0].getValue());
//		JSONObject jj = new JSONObject();
//		jj.put(request.getCookies()[0].getName(), request.getCookies()[0].getValue());
//		json.add(jj);
//		if(request.getCookies()!=null)
//			response.getOutputStream().print(request.getCookies().length);
//		else
//			response.getOutputStream().print("WHY");
//		response.getOutputStream().print(request.getSession().getId());
		response.getOutputStream().print(json.toString());
	}

}

