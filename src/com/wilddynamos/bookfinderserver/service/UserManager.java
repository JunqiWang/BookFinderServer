package com.wilddynamos.bookfinderserver.service;

import com.wilddynamos.bookfinderserver.dao.UserDao;
import com.wilddynamos.bookfinderserver.model.User;

public class UserManager extends BaseManager<User> {

	private UserDao userDao;

	public UserManager() {
		userDao = new UserDao();
	}

	@Override
	public UserDao getEntityDao() {
		return userDao;
	}
}