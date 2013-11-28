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
import com.wilddynamos.bookappserver.service.RequestManager;

public class RequestListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4953808045786008751L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String bookId = request.getParameter("id");
		
		if(currentPage == null)
			currentPage = 1;

		RequestManager rm = new RequestManager();
		List<Request> requesters = rm.findByProp("book_id", bookId, "request_time", 
				currentPage, null, 1);
		rm.close();
		
		System.out.println(bookId);
		
		JSONArray json = new JSONArray();
		String path = this.getServletContext().getRealPath("/profile_photo");
		System.out.println(path);
		
		for(Request r: requesters) {
			JSONObject jo = new JSONObject();
			
			jo.put("id", r.getId());
			System.out.println(r.getRequesterId());
			jo.put("name", r.getRequester().getName());

			File file = new File(path + "/" + String.valueOf(r.getRequesterId()) + ".jpg");
			String photo = null;
			if(file.exists() && file.isFile()) {
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024 * 1024];
			
				try {
					for(int readNum; (readNum = fis.read(buf)) != -1;)
						bos.write(buf, 0, readNum);
				} catch(Exception e) {
					e.printStackTrace();
				}
				byte[] bytes = bos.toByteArray();
				photo = new String(bytes, Charset.forName("ISO-8859-1"));
				fis.close();
			}
			jo.put("photo", photo);
			
			json.add(jo);
		}
		System.out.println(json.size());
		System.out.println(json);
		response.getWriter().println(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
