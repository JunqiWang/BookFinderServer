package com.wilddynamos.bookfinderserver.service;

import com.wilddynamos.bookfinderserver.dao.BaseDao;
import com.wilddynamos.bookfinderserver.dao.BookDao;
import com.wilddynamos.bookfinderserver.model.Book;

import java.util.*;

public class BookManager extends BaseManager<Book> {

	private BookDao bookDao;

	public BookManager() {
		bookDao = new BookDao();
	}

	@Override
	protected BaseDao<Book> getEntityDao() {
		return bookDao;
	}

	@Override
	public List<Book> findAll(String order, Integer currentPage,
			Integer pageSize) {
		List<Book> books = super.findAll(order, currentPage, pageSize);

		UserManager um = new UserManager();
		for (Book book : books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null,
					null, 1, 1).get(0));
		um.close();

		return books;
	}

	@Override
	public List<Book> findByProp(String name, String value, String order,
			Integer currentPage, Integer pageSize, int condition) {

		List<Book> books = super.findByProp(name, value, order, currentPage,
				pageSize, condition);

		UserManager um = new UserManager();
		for (Book book : books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null,
					null, 1, 1).get(0));
		um.close();

		return books;
	}

	public List<Book> findAllAvailableBooks(Integer currentPage,
			Integer pageSize, Boolean sOrR, String search, Integer userId) {

		List<Book> books = bookDao.findAllAvailableBooks(currentPage, pageSize,
				sOrR, search, userId);

		UserManager um = new UserManager();
		for (Book book : books)
			book.setOwner(um.findByProp("id", book.getOwnerId() + "", null,
					null, 1, 1).get(0));
		um.close();

		return books;
	}

}