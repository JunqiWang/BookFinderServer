package com.wilddynamos.bookappserver.servlet.mybooks;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.service.BookManager;

public class PostEditBookServlet extends HttpServlet {

	private static final long serialVersionUID = 8747474466079681394L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String isRent = request.getParameter("isRent");
		String bookId = request.getParameter("id");
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));
		String description = request.getParameter("description");

		String imageString = request.getParameter("cover");
		String path = "/Users/JunqiWang/CMU/workspace/J2EE/BookAppServer/WebContent/book_cover";// this.getServletContext().getRealPath("/book_cover");
		byte[] coverImage = imageString.getBytes(Charset.forName("ISO-8859-1"));
		InputStream in = new ByteArrayInputStream(coverImage);
		BufferedImage bufferedImage = ImageIO.read(in);
		// Getting exception at this line

		BookManager bm = new BookManager();
		Book book = null;

		if (bookId != null && !"".equals(bookId)) {
			book = bm.findByProp("id", bookId, null, null, null, 1).get(0);
			ImageIO.write(bufferedImage, "png", new File(path + "/" + bookId
					+ ".png"));
			File file = new File(path + "/" + book.getCoverPath());
			try {
				file.delete();
			} catch (Exception e) {
			}
			book.setCoverPath(bookId + ".png");
		} else {
			book = new Book();
			book.setOwnerId(userId);
			Date d = new Date();
			book.setPostTime(d);
			book.setStatus(false);

			String coverPath = userId
					+ "_"
					+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(book
							.getPostTime()) + ".png";
			ImageIO.write(bufferedImage, "png",
					new File(path + "/" + coverPath));
			book.setCoverPath(coverPath);
		}

		book.setName(name);
		book.setPrice(price);
		book.setDescription(description);

		if ("true".equals(isRent)) {
			book.setsOrR(false);
			String perValue = request.getParameter("perValue");
			if ("true".equals(perValue))
				book.setPer(true);
			else if ("false".equals(perValue))
				book.setPer(true);

			book.setAvailableTime(Integer.parseInt(request
					.getParameter("duration")));
		} else
			book.setsOrR(true);

		int result = bm.addOrUpdate(book);

		if (result < 0)
			response.getWriter().println("-1");
		else
			response.getWriter().println("1");
	}

}
