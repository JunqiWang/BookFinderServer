package com.wilddynamos.bookappserver.servlet;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wilddynamos.bookappserver.ActiveUserPool;

public class LongConnRegServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		
//		UserManager um = new UserManager();
//		List<User> users = um.findByProp("id", id, null, null, 1, 1);
//		um.close();
		for(Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			System.out.println(name+"="+request.getHeader(name));
			
		}
		for(Iterator<String> e = response.getHeaderNames().iterator(); e.hasNext();)
		System.err.println(e.next());
		ActiveUserPool.userId2response.put(id, response);
		System.out.println(ActiveUserPool.userId2response.get(1));
		
		
			synchronized(ActiveUserPool.sb) {
//				if(ActiveUserPool.running)
//					break;
				
				try {
					while("1".equals(ActiveUserPool.sb.toString()))
						ActiveUserPool.sb.wait();
					
				} catch(Exception e) {
					
				}
			}

		response.getWriter().println("hi");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	
}

