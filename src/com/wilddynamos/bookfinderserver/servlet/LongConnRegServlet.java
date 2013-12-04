package com.wilddynamos.bookfinderserver.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet that provides the registration of a long connection.
 * Used for pushed notifications.
 * 
 * @author JunqiWang
 * 
 */
@WebServlet(asyncSupported = true)
public class LongConnRegServlet extends HttpServlet {

	private static final long serialVersionUID = 6844621925820794386L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// check if user is still alive
		if (response != null) {
			// this line is important, can't tell why
			response.setContentType("text/html; charset=UTF-8");

			// extract response object from async request
			ServletResponse res = (ServletResponse) request
					.getAttribute("response");
			Integer bookId = (Integer) request.getAttribute("bookId");

			PrintWriter out = null;
			if (res != null) {
				out = res.getWriter();
				if (bookId != null) {
					if (bookId > 0) {
						out.println("Req" + bookId);
						System.out.println("push req " + bookId);
					} else {
						out.println("Res" + (-bookId));
						System.out.println("push res " + bookId);
					}
				}
				// this line is important
				out.flush();
			}

			request.startAsync(request, response);
			new MessageSender(request.getAsyncContext()).start();
		}
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

			actx.getRequest().setAttribute("response", actx.getResponse());

			synchronized (ActiveUserPool.userId2bookIds) {

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
