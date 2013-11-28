package com.wilddynamos.bookappserver.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(asyncSupported = true)
public class LongConnRegServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// this line is important
		response.setContentType("text/html; charset=UTF-8");
		
		Integer bookId = (Integer) request.getAttribute("bookId");

		PrintWriter out = response.getWriter();
		if(bookId != null) {
			if(bookId > 0)
				out.println("Req" + bookId);
			else {System.out.println("Res" + (-bookId));
				out.println("Res" + (-bookId));
			}
		} else
			out.println("Req");
		// this line is important
		out.flush();

		request.startAsync(request, response);
		new MessageSender(request.getAsyncContext()).start();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	private class MessageSender extends Thread {

		AsyncContext actx;

		public MessageSender(AsyncContext actx) {
			this.actx = actx;
			actx.setTimeout(1000 * 60 * 30);
			this.setPriority(MAX_PRIORITY);
		}

		@Override
		public void run() {
			Integer id = Integer.parseInt(actx.getRequest().getParameter("id"));

			synchronized (ActiveUserPool.userId2bookIds) {

				// synchronized(ActiveUserPool.ownerIds) {
				// try {
				// while(!ActiveUserPool.ownerIds.contains(id))
				// ActiveUserPool.ownerIds.wait();
				//
				// ActiveUserPool.ownerIds.remove(id);
				// } catch(Exception e) {
				// }
				try {
					while (!ActiveUserPool.userId2bookIds.containsKey(id))
						ActiveUserPool.userId2bookIds.wait();

					if (ActiveUserPool.userId2bookIds.get(id).size() > 0) {
						actx.getRequest().setAttribute("bookId",
								ActiveUserPool.userId2bookIds.get(id).get(0));
						ActiveUserPool.userId2bookIds.get(id).remove(0);
					} else
						ActiveUserPool.userId2bookIds.remove(id);

				} catch (Exception e) {
				}
			}

			actx.dispatch();
		}
	}
}
