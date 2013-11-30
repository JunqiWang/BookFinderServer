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

import com.wilddynamos.bookappserver.model.Request;
import com.wilddynamos.bookappserver.model.User;
import com.wilddynamos.bookappserver.service.RequestManager;
import com.wilddynamos.bookappserver.service.UserManager;

public class RequestDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 4953808045786008751L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("id");

		RequestManager rm = new RequestManager();
		List<Request> requesters = rm.findByProp("id", id, null, null, null, 1);
		rm.close();

		int requesterId = requesters.get(0).getRequesterId();

		UserManager um = new UserManager();
		List<User> users = um.findByProp("id", String.valueOf(requesterId),
				null, null, null, 1);
		um.close();

		JSONArray json = new JSONArray();
		String path = this.getServletContext().getRealPath("/profile_photo");

		for (User u : users) {
			JSONObject jo = new JSONObject();

			jo.put("id", u.getId());
			jo.put("name", u.getName());
			jo.put("gender", u.getGender() ? "Male" : "Female");
			jo.put("campus", u.getCampus());
			jo.put("contact", u.getContact());
			jo.put("address", u.getAddress());

			File file = new File(path + "/" + String.valueOf(u.getId())
					+ ".jpg");
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
		}

		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
