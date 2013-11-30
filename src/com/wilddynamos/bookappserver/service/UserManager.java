package com.wilddynamos.bookappserver.service;

import com.wilddynamos.bookappserver.dao.UserDao;
import com.wilddynamos.bookappserver.model.User;

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