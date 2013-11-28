package com.wilddynamos.bookappserver.dao;

import java.sql.*;
import java.util.*;

import com.wilddynamos.bookappserver.model.User;

public class UserDao {
	
	public static final String address = "jdbc:mysql://10.0.23.238:3306/book_app";
	
	private static final Integer DEFAULT_USER_PAGESIZE = 10;
	private Connection conn;
	private Statement stmt;
	
	public UserDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(address, "zhe", null);
			stmt = conn.createStatement();
		} catch (Exception e) {
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
	
	public int add(User user) {
		try {
			return stmt.executeUpdate(
					"INSERT INTO user(id, email, password, name, "
					+ "gender, campus, contact, address, photo_path)"
					+ " VALUES(null, '"
					+ user.getEmail() + "', '"
					+ user.getPassword() + "', '"
					+ user.getName() + "', "
					+ user.getGender() + ", "
					+ (user.getCampus() == null ? null : "'" + user.getCampus() + "'") + ", "
					+ (user.getContact() == null ? null : "'" + user.getCampus() + "'") + ", "
					+ (user.getAddress() == null ? null : "'" + user.getCampus() + "'") + ", "
					+ (user.getAddress() == null ? null : "'" + user.getCampus() + "'") + ");");
			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeById(Integer id) {
		try {
			return stmt.executeUpdate("DELETE FROM user WHERE id = " + id + ";");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeByProp(String name, String value, String order, Integer limit) {
		try {
			return stmt.executeUpdate("DELETE FROM user WHERE " 
						+ name + " = '" + value + "' ORDER BY " + order + " LIMIT " + limit + ";");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int update(User user) {
		try {
			return stmt.executeUpdate(
					"UPDATE user SET "
					+ "email = '" + user.getEmail() + "', "
					+ "password = '" + user.getPassword() + "', "
					+ "name = '" + user.getName() + "', "
					+ "gender = " + user.getGender() + ", "
					+ "campus = " + (user.getCampus() == null ? null : "'" + user.getCampus() + "'") + ", "
					+ "contact = " + (user.getContact() == null ? null : "'" + user.getContact() + "'") + ", "
					+ "address = " + (user.getAddress() == null ? null : "'" + user.getAddress() + "'") + ", "
					+ "photo_path = " + (user.getPhotoPath() == null ? null : "'" + user.getPhotoPath() + "'") + " "
					+ "WHERE id = " + user.getId() + ";");
			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int addOrUpdate(User user) {
		if(user.getId() == null)
			return this.add(user);
		else
			return this.update(user);
	}
	
	public List<User> findAll(String order, Integer currentPage, Integer pageSize) {
		List<User> users = new ArrayList<User>();
		try {
			pageSize = (pageSize == null ? DEFAULT_USER_PAGESIZE : pageSize);
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM user ORDER BY " + order 
					+ " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
					+ pageSize + ";");

			while(rs.next())
				users.add(new User(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getString(3),
								   rs.getString(4), 
								   rs.getBoolean(5), 
								   rs.getString(6),
								   rs.getString(7),
								   rs.getString(8),
								   rs.getString(9)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public List<User> findByProp(String name, String value, String order, 
			Integer currentPage, Integer pageSize, int condition) {
		
		List<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM user WHERE " + name;
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
			pageSize = (pageSize == null ? DEFAULT_USER_PAGESIZE : pageSize);
			sql += " ORDER BY " + order 
				   + " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
				   + pageSize + ";";
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
				users.add(new User(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getString(3),
								   rs.getString(4), 
								   rs.getBoolean(5), 
								   rs.getString(6),
								   rs.getString(7),
								   rs.getString(8),
								   rs.getString(9)));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
}