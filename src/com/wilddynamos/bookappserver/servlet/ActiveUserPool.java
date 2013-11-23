package com.wilddynamos.bookappserver.servlet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.wilddynamos.bookappserver.model.User;

public abstract class ActiveUserPool {

	public static Map<String, User> session2user
		= new ConcurrentHashMap<String, User>();
	
	public static Set<Integer> ownerIds = new HashSet<Integer>();
	
}
