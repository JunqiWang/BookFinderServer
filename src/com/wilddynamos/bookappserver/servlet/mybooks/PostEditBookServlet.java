package com.wilddynamos.bookappserver.servlet.mybooks;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.Book;
import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.BookManager;
import com.wilddynamos.bookappserver.service.UserManager;

public class PostEditBookServlet extends HttpServlet {

	private static final long serialVersionUID = 8747474466079681394L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String isRent = request.getParameter("isRent");
		String bookId = request.getParameter("id");
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));
		String description = request.getParameter("description");
		System.out.println(userId);
//		String path = this.getServletContext().getRealPath("/profile_photo");
//		byte[] profileImage = imageString.getBytes(Charset.forName("ISO-8859-1"));
//		InputStream in = new ByteArrayInputStream(profileImage);
//        BufferedImage bufferedImage = ImageIO.read(in);
//
//        //Getting exception at this line
//        ImageIO.write(bufferedImage, "png", new File(path+"/user"+String.valueOf(id)+".png"));
		
		BookManager bm = new BookManager();
		Book book = null;System.out.println(bookId);
		if(bookId != null && !"".equals(bookId))
			book = bm.findByProp("id", bookId, null, null, null, 1).get(0);
		else {
			book = new Book();
			book.setOwnerId(userId);
			book.setPostTime(new Date());
			book.setStatus(false);
		}
		
		book.setName(name);
		book.setPrice(price);
		book.setDescription(description);
		
		if("true".equals(isRent)) {
			book.setsOrR(false);
			String perValue = request.getParameter("perValue");
			if("true".equals(perValue))
				book.setPer(true);
			else if("false".equals(perValue))
				book.setPer(true);
				
			book.setAvailableTime(Integer.parseInt(request.getParameter("duration")));
		} else
			book.setsOrR(false);
		
		int result = bm.addOrUpdate(book);
		if (result < 0)
			response.getWriter().println("-1");
		else
			response.getWriter().println("1");
	}

}
