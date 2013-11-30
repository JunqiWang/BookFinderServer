package com.wilddynamos.bookappserver.servlet.mybooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.RequestManager;
import com.wilddynamos.bookappserver.servlet.ActiveUserPool;

public class AcceptRequestServlet extends HttpServlet {

	private static final long serialVersionUID = -5407421410289191786L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String bookId = request.getParameter("bookId");
		String requesterId = request.getParameter("requesterId");

		RequestManager rm = new RequestManager();
		List<Request> requesters = rm.findByProp("book_id", bookId,
				"request_time", null, Integer.MAX_VALUE, 1);

		for (Request r : requesters) {
			if (r.getRequesterId() == Integer.parseInt(requesterId))
				r.setStatus(true);
			else
				r.setStatus(false);
		}

		for (Request r : requesters)
			if (rm.update(r) <= 0) {
				rm.close();
				response.getWriter().println("-1");
				return;
			}

		rm.close();

		BookManager bm = new BookManager();
		List<Book> books = bm.findByProp("id", bookId, null, null, null, 1);
		Book book = books.get(0);
		book.setStatus(true);
		bm.update(book);

		synchronized (ActiveUserPool.userId2bookIds) {
			for (Request r : requesters) {
				if (ActiveUserPool.userId2bookIds.get(r.getRequesterId()) == null)
					ActiveUserPool.userId2bookIds.put(r.getRequesterId(),
							new ArrayList<Integer>());
				ActiveUserPool.userId2bookIds.get(r.getRequesterId()).add(
						-Integer.parseInt(bookId));
				System.out.println(ActiveUserPool.userId2bookIds.get(
						r.getRequesterId()).get(0));
			}

			ActiveUserPool.userId2bookIds.notifyAll();
		}

		response.getWriter().println("1");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
