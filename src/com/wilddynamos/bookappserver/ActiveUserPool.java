package com.wilddynamos.bookappserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import com.wilddynamos.bookappserver.model.User;

public abstract class ActiveUserPool {

	public static Map<String, User> session2user
		= new ConcurrentHashMap<String, User>();
	
	public static Map<Integer, HttpServletResponse> userId2response
		= new ConcurrentHashMap<Integer, HttpServletResponse>();
	
	public static StringBuilder sb = new StringBuilder("1");
}
