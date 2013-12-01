package com.wilddynamos.bookfinderserver.servlet.profile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookfinderserver.model.User;
import com.wilddynamos.bookfinderserver.service.UserManager;

public class EditProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 7034566395932759249L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		UserManager um = new UserManager();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String campus = request.getParameter("campus");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String imageString = request.getParameter("image");

		String path = "/Users/JunqiWang/CMU/workspace/J2EE/BookFinderServer/WebContent/profile_photo";// this.getServletContext().getRealPath("/profile_photo");
		byte[] profileImage = imageString.getBytes(Charset
				.forName("ISO-8859-1"));
		InputStream in = new ByteArrayInputStream(profileImage);
		BufferedImage bufferedImage = ImageIO.read(in);

		// Getting exception at this line
		File file = new File(path + "/" + id + ".jpg");
		try {
			file.delete();
		} catch (Exception e) {
		}
		file.createNewFile();
		ImageIO.write(bufferedImage, "jpg", file);

		List<User> users = um.findByProp("id", id, null, null, 1, 1);
		User user = users.get(0);
		user.setName(name);
		user.setGender("1".equals(gender));
		user.setCampus(campus);
		user.setContact(contact);
		user.setAddress(address);
		user.setPhotoPath(id + ".jpg");

		int result = um.update(user);
		if (result > 0)
			response.getWriter().println("1");
		else
			response.getWriter().println("-1");
	}

}
