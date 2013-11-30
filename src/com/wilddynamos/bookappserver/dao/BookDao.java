package com.wilddynamos.bookappserver.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wilddynamos.bookappserver.model.Book;

public class BookDao extends BaseDao<Book> {
	
	private static final Integer DEFAULT_BOOK_PAGESIZE = 15;
	
	public BookDao() {
		super();
	}
	
	public int add(Book book) {
		try {
			return stmt.executeUpdate(
					"INSERT INTO book(id, name, price, per, available_time, likes,"
					+ "s_or_r, status, description, post_time, cover_path, owner_id)"
					+ " VALUES(null, '"
					+ book.getName() + "', "
					+ book.getPrice() + ", "
					+ book.getPer() + ", "
					+ book.getAvailableTime() + ", "
					+ 0 + ", "
					+ book.getsOrR() + ", "
					+ book.getStatus() + ", '"
					+ book.getDescription() + "', '"
					+ new SimpleDateFormat(DATE_TIME_FORMAT).format(book.getPostTime()) + "', '"
					+ book.getCoverPath() + "', '"
					+ book.getOwnerId() + "');");
			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeById(Integer id) {
		try {
			return stmt.executeUpdate("DELETE FROM book WHERE id='" + id + "';");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeByProp(String name, String value, String order, Integer limit) {
		try {
			return stmt.executeUpdate("DELETE FROM book WHERE " 
						+ name + " = '" + value + "' ORDER BY " + order + " LIMIT " + limit + ";");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int update(Book book) {
		try {
			return stmt.executeUpdate("UPDATE book SET "
									   + "name = '" + book.getName() + "', "
									   + "price = " + book.getPrice() + ", "
									   + "per = " + book.getPer() + ", "
									   + "available_time = " + book.getAvailableTime() + ", "
									   + "likes = " + book.getLikes() + ", "
									   + "s_or_r = " + book.getsOrR() + ", "
									   + "status = " + book.getStatus() + ", "
									   + "description = '" + book.getDescription() + "', "
									   + "post_time = '" + new SimpleDateFormat(DATE_TIME_FORMAT).format(book.getPostTime()) + "', "
									   + "cover_path = '" + book.getCoverPath() + "', "
									   + "owner_id = " + book.getOwnerId() + " "
									   + "WHERE id = " + book.getId() + ";");
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int addOrUpdate(Book book) {
		if(book.getId() == null)
			return this.add(book);
		else
			return this.update(book);
	}
	
	public List<Book> findAll(String order, Integer currentPage, Integer pageSize) {
		List<Book> books = new ArrayList<Book>();
		try {
			pageSize = (pageSize == null ? DEFAULT_BOOK_PAGESIZE : pageSize);
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM book ORDER BY " + order
					+ " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
					+ pageSize + ";");

			while(rs.next())
				books.add(new Book(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getInt(3),
								   rs.getBoolean(4), 
								   rs.getInt(5), 
								   rs.getInt(6),
								   rs.getBoolean(7),
								   rs.getBoolean(8),
								   rs.getString(9),
								   rs.getDate(10),
								   rs.getString(11),
								   rs.getInt(12)));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	public List<Book> findByProp(String name, String value, 
			String order, Integer currentPage, Integer pageSize, int condition) {
		List<Book> books = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM book WHERE " + name;
			switch(condition) {
				case 1: sql += " = '" + value + "'"; break;
				case 2: sql += " like '%" + value + "%'"; break;
				case 3: sql += " in " + value + ""; break;
				case 4: sql += " < '" + value + "'"; break;
				case 5: sql += " <= '" + value + "'"; break;
				case 6: sql += " > '" + value + "'"; break;
				case 7: sql += " >= '" + value + "'"; break;
				case 8: sql += " <> '" + value + "'"; break;
				default: sql += " = '" + value + "'";
			}
			pageSize = (pageSize == null ? DEFAULT_BOOK_PAGESIZE : pageSize);
			
			sql += " ORDER BY " + order 
				   + " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
				   + pageSize + ";";
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
				books.add(new Book(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getInt(3),
								   rs.getBoolean(4), 
								   rs.getInt(5), 
								   rs.getInt(6),
								   rs.getBoolean(7),
								   rs.getBoolean(8),
								   rs.getString(9),
								   rs.getDate(10),
								   rs.getString(11),
								   rs.getInt(12)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	public List<Book> findAllAvailableBooks(Integer currentPage, 
			Integer pageSize, Boolean sOrR, String search, Integer userId) {
		
		List<Book> books = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM book WHERE status = 0 AND owner_id <> '" + userId + "'";
			if(sOrR != null)
				sql += " AND s_or_r = " + sOrR;
			if(search != null && !"".equals(search))
				sql += " AND name LIKE '%" + search + "%'";
			sql += " ORDER BY post_time desc";
			
			pageSize = pageSize == null ? DEFAULT_BOOK_PAGESIZE : pageSize;
			sql += " LIMIT " + (currentPage == null ? 0 : (currentPage - 1) * pageSize) + ", " 
				   + pageSize + ";";

			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
				books.add(new Book(rs.getInt(1), 
								   rs.getString(2), 
								   rs.getInt(3),
								   rs.getBoolean(4), 
								   rs.getInt(5), 
								   rs.getInt(6),
								   rs.getBoolean(7),
								   rs.getBoolean(8),
								   rs.getString(9),
								   rs.getDate(10),
								   rs.getString(11),
								   rs.getInt(12)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return books;
	}
}