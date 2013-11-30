package com.wilddynamos.bookappserver.service;

import com.wilddynamos.bookappserver.dao.RequestDao;
import com.wilddynamos.bookappserver.model.Request;

import java.util.*;

public class RequestManager extends BaseManager<Request> {

	private RequestDao requestDao;

	public RequestManager() {
		requestDao = new RequestDao();
	}

	@Override
	public RequestDao getEntityDao() {
		return requestDao;
	}

	@Override
	public List<Request> findAll(String order, Integer currentPage,
			Integer pageSize) {
		List<Request> requests = super.findAll(order, currentPage, pageSize);

		UserManager um = new UserManager();
		for (Request request : requests)
			request.setRequester(um.findByProp("id",
					request.getRequesterId() + "", null, null, 1, 1).get(0));
		um.close();

		return requests;
	}

	@Override
	public List<Request> findByProp(String name, String value, String order,
			Integer currentPage, Integer pageSize, int condition) {

		List<Request> requests = super.findByProp(name, value, order,
				currentPage, pageSize, condition);

		UserManager um = new UserManager();
		for (Request request : requests)
			request.setRequester(um.findByProp("id",
					request.getRequesterId() + "", null, null, 1, 1).get(0));
		um.close();

		return requests;
	}

	public Request findByBookAndRequester(String bookId, String requesterId) {

		Request request = requestDao
				.findByBookAndRequester(bookId, requesterId);

		UserManager um = new UserManager();
		request.setRequester(um.findByProp("id", request.getRequesterId() + "",
				null, null, 1, 1).get(0));
		um.close();

		return request;
	}

}