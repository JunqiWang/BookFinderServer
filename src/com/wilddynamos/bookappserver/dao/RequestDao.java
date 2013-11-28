package com.wilddynamos.bookappserver.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wilddynamos.bookappserver.model.Request;

public class RequestDao {
	private static final Integer DEFAULT_REQUEST_PAGESIZE = 10;
	private Connection conn;
	private Statement stmt;
	
	public RequestDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(UserDao.address, "root", null);
			stmt = conn.createStatement();
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int add(Request request) {
		try {
			return stmt.executeUpdate(
					"INSERT INTO request(id, message, status, "
					+ "request_time, book_id, requester_id)"
					+ " VALUES(null, '"
					+ request.getMessage() + "', "
					+ request.getStatus() + ", '"
					+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(request.getRequestTime()) + "', '"
					+ request.getBookId() + "', '"
					+ request.getRequesterId() + "');");
			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeById(Integer id) {
		try {
			return stmt.executeUpdate("DELETE FROM request WHERE id='" + id + "';");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeByProp(String name, String value, String order, Integer limit) {
		try {
			return stmt.executeUpdate("DELETE FROM request WHERE " 
						+ name + " = '" + value + "' ORDER BY " + order + " LIMIT " + limit + ";");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int update(Request request) {
		try {
			return stmt.executeUpdate("UPDATE request SET "
									   + "message = '" + request.getMessage() + "', "
									   + "status = " + request.getStatus() + ", "
									   + "request_time = '" + request.getRequestTime() + "', "
									   + "book_id = '" + request.getBookId() + "', "
									   + "requester_id = '" + request.getRequesterId() + "' "
									   + "WHERE id = '" + request.getId() + "';");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int addOrUpdate(Request request) {
		if(request.getId() == null)
			return this.add(request);
		else
			return this.update(request);
	}
	
	public List<Request> findAll(String order, Integer currentPage, Integer pageSize) {
		List<Request> requests = new ArrayList<Request>();
		try {
			pageSize = (pageSize == null ? DEFAULT_REQUEST_PAGESIZE : pageSize);
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM request ORDER BY " + order
					+ " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
					+ pageSize + ";");

			while(rs.next())
				requests.add(new Request(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getBoolean(3),
								   rs.getDate(4), 
								   rs.getInt(5), 
								   rs.getInt(6)));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return requests;
	}
	
	public List<Request> findByProp(String name, String value, 
			String order, Integer currentPage, Integer pageSize, int condition) {
		List<Request> requests = new ArrayList<Request>();
		try {
			String sql = "SELECT * FROM request WHERE " + name;
			switch(condition) {
				case 1: sql += " = '" + value + "'"; break;
				case 2: sql += " like '%" + value + "%'"; break;
				case 3: sql += " in " + value + ""; break;
				case 4: sql += " < '" + value + "'"; break;
				case 5: sql += " <= '" + value + "'"; break;
				case 6: sql += " > '" + value + "'"; break;
				case 7: sql += " >= '" + value + "'"; break;
				default: sql += " = '" + value + "'";
			}
			pageSize = (pageSize == null ? DEFAULT_REQUEST_PAGESIZE : pageSize);
			
//			sql += " AND status = null";
			System.out.println(sql);
			sql += " ORDER BY " + order 
				   + " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
				   + pageSize + ";";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
				requests.add(new Request(rs.getInt(1), 
									     rs.getString(2), 
									     rs.getObject(3) == null ? null : rs.getBoolean(3),
									     rs.getDate(4), 
									     rs.getInt(5), 
									     rs.getInt(6)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return requests;
	}
	
	public List<Request> findAllAvailableRequests(Integer currentPage, Integer pageSize) {
		List<Request> requests = new ArrayList<Request>();
		try {
			String sql = "SELECT * FROM request WHERE status = 0 ORDER BY request_time desc ";
			
			pageSize = (pageSize == null ? DEFAULT_REQUEST_PAGESIZE : pageSize);
			sql += " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
				   + pageSize + ";";
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
				requests.add(new Request(rs.getInt(1), 
										 rs.getString(2), 
										 rs.getBoolean(3),
										 rs.getDate(4), 
										 rs.getInt(5), 
										 rs.getInt(6)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return requests;
	}
}