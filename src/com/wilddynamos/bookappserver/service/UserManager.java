package com.wilddynamos.bookappserver.service;

import com.wilddynamos.bookappserver.dao.UserDao;
import com.wilddynamos.bookappserver.model.User;

import java.util.*;

public class UserManager {
	private UserDao userDao;
	
	public UserManager() {
		userDao = new UserDao();
	}
	
	public void close() {
		userDao.close();
	}
	
	public int add(User user) {
		return userDao.add(user);
	}
	
	public int removeById(Integer id) {
		return userDao.removeById(id);
	}
	
	public int removeByProp(String name, String value, String order, int limit) {
		return userDao.removeByProp(name, value, order, limit);
	}
	
	public int update(User user) {
		return userDao.update(user);
	}
	
	public int addOrUpdate(User user) {
		return userDao.addOrUpdate(user);
	}
	
	public List<User> findAll(String order, Integer currentPage, Integer pageSize) {
		return userDao.findAll(order, currentPage, pageSize);
	}
	
	public List<User> findByProp(String name, String value, String order, 
			Integer currentPage, Integer pageSize, int condition) {
		
		return userDao.findByProp(name, value, order, currentPage, pageSize, condition);
	}
}