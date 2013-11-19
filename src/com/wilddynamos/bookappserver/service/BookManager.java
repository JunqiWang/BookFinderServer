package com.wilddynamos.bookappserver.service;

import com.wilddynamos.bookappserver.dao.BookDao;
import com.wilddynamos.bookappserver.model.Book;

import java.util.*;

public class BookManager {
	private BookDao bookDao;
	
	public BookManager() {
		bookDao = new BookDao();
	}
	
	public void close() {
		bookDao.close();
	}
	
	public int add(Book book) {
		return bookDao.add(book);
	}
	
	public int removeById(Integer id) {
		return bookDao.removeById(id);
	}
	
	public int removeByProp(String name, String value, String order, int limit) {
		return bookDao.removeByProp(name, value, order, limit);
	}
	
	public int update(Book book) {
		return bookDao.update(book);
	}
	
	public int addOrUpdate(Book book) {
		return bookDao.addOrUpdate(book);
	}
	
	public List<Book> findAll(String order, Integer currentPage, Integer pageSize) {
		List<Book> books =  bookDao.findAll(order, currentPage, pageSize);
		
		UserManager um = new UserManager();
		for(Book book: books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null, null, 1, 1).get(0));
		um.close();
		
		return books;
	}
	
	public List<Book> findByProp(String name, String value, String order, 
			Integer currentPage, Integer pageSize, Integer condition) {
		
		List<Book> books =  bookDao.findByProp(name, value, order, currentPage, pageSize, condition);
		
		UserManager um = new UserManager();
		for(Book book: books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null, null, 1, 1).get(0));
		um.close();
		
		return books;
	}
	
	public List<Book> findAllAvailableBooks(Integer currentPage, 
			Integer pageSize, Boolean sOrR, String search) {
		List<Book> books =  bookDao.findAllAvailableBooks(currentPage, pageSize, sOrR, search);
		
		UserManager um = new UserManager();
		for(Book book: books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null, null, 1, 1).get(0));
		um.close();
		
		return books;
	}
}