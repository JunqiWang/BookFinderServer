package com.wilddynamos.bookfinderserver.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wilddynamos.bookfinderserver.model.User;

/**
 * Record active users
 * 
 * @author JunqiWang
 * 
 */
public abstract class ActiveUserPool {

	/**
	 * Map with key of session id and value of user object
	 */
	public static Map<String, User> session2user = new ConcurrentHashMap<String, User>();

	/**
	 * For push notifications. Key is the id of user that has new messages
	 */
	public static Map<Integer, List<Integer>> userId2bookIds = new HashMap<Integer, List<Integer>>();

}
