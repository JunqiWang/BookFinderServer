package com.wilddynamos.bookappserver.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(asyncSupported = true)
public class LongConnRegServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		//this line is important
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("New");
		//this line is important
		out.flush();System.out.println("here"+request.getParameter("id"));

		request.startAsync(request, response);
		new MessageSender(request.getAsyncContext()).start();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	private static class MessageSender extends Thread {
		
		AsyncContext actx;
		
		public MessageSender(AsyncContext actx) {
			this.actx = actx;
			actx.setTimeout(1000 * 60 * 30);
			this.setPriority(MAX_PRIORITY);
		}
		
		@Override
		public void run() {
			Integer id = Integer.parseInt(actx.getRequest().getParameter("id"));
			
			synchronized(ActiveUserPool.ownerIds) {
				try {
					while(!ActiveUserPool.ownerIds.contains(id))
						ActiveUserPool.ownerIds.wait();
					System.out.println(id);
					ActiveUserPool.ownerIds.remove(id);System.out.println("get it");
				} catch(Exception e) {
				}
			}
			actx.dispatch();
		}
	}
}
