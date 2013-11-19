package com.wilddynamos.bookappserver.service;

import com.wilddynamos.bookappserver.dao.RequestDao;
import com.wilddynamos.bookappserver.model.Request;

import java.util.*;

public class RequestManager {
	private RequestDao requestDao;
	
	public RequestManager() {
		requestDao = new RequestDao();
	}
	
	public void close() {
		requestDao.close();
	}
	
	public int add(Request request) {
		return requestDao.add(request);
	}
	
	public int removeById(Integer id) {
		return requestDao.removeById(id);
	}
	
	public int removeByProp(String name, String value, String order, int limit) {
		return requestDao.removeByProp(name, value, order, limit);
	}
	
	public int update(Request request) {
		return requestDao.update(request);
	}
	
	public int addOrUpdate(Request request) {
		return requestDao.addOrUpdate(request);
	}
	
	public List<Request> findAll(String order, Integer currentPage, Integer pageSize) {
		List<Request> requests =  requestDao.findAll(order, currentPage, pageSize);
		
		UserManager um = new UserManager();
		for(Request request: requests)
			request.setRequester(um.findByProp("id", request.getRequesterId() + "", null, null, 1, 1).get(0));
		um.close();
		
		return requests;
	}
	
	public List<Request> findByProp(String name, String value, String order, 
			Integer currentPage, Integer pageSize, int condition) {
		
		List<Request> requests =  requestDao.findByProp(name, value, order, currentPage, pageSize, condition);
		
		UserManager um = new UserManager();
		for(Request request: requests)
			request.setRequester(um.findByProp("id", request.getRequesterId() + "", null, null, 1, 1).get(0));
		um.close();
		
		return requests;
	}
	
}