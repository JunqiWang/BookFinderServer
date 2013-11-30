package com.wilddynamos.bookappserver.servlet.profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.UserManager;

public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 8539175548905858127L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		UserManager um = new UserManager();
		List<User> users = um.findByProp("id", id, null, null, null, 1);
		um.close();

		if (users.size() == 0) {
			response.getWriter().println("-1");
			return;
		}

		JSONArray json = new JSONArray();
		String path = this.getServletContext().getRealPath("/profile_photo");

		JSONObject jo = new JSONObject();

		jo.put("email", users.get(0).getEmail());
		jo.put("name", users.get(0).getName());
		jo.put("gender", users.get(0).getGender());
		jo.put("campus", users.get(0).getCampus());
		jo.put("contact", users.get(0).getContact());
		jo.put("address", users.get(0).getAddress());

		File file = new File(path + "/" + id + ".jpg");

		String photo = null;
		if (file.exists() && file.isFile()) {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 1024];

			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;)
					bos.write(buf, 0, readNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			byte[] bytes = bos.toByteArray();
			photo = new String(bytes, Charset.forName("ISO-8859-1"));
			fis.close();
		}
		jo.put("photo", photo);

		json.add(jo);

		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
