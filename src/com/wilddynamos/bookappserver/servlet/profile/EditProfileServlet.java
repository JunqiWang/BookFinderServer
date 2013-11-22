package com.wilddynamos.bookappserver.servlet.profile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		UserManager um = new UserManager();
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String campus = request.getParameter("campus");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String imageString = request.getParameter("image");
		
		String path = this.getServletContext().getRealPath("/profile_photo");
		byte[] profileImage = imageString.getBytes(Charset.forName("ISO-8859-1"));
		InputStream in = new ByteArrayInputStream(profileImage);
        BufferedImage bufferedImage = ImageIO.read(in);

        //Getting exception at this line
        ImageIO.write(bufferedImage, "png", new File(path+"/user"+String.valueOf(id)+".png"));
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setGender(gender.equals("M"));
		user.setCampus(campus);
		user.setContact(contact);
		user.setAddress(address);
		
		int result = um.update(user);
		if (result > 0)
			response.getWriter().println("1");
		else if (result == 0)
			response.getWriter().println("0");
		else if (result < 0)
			response.getWriter().println("-1");
	}

}
