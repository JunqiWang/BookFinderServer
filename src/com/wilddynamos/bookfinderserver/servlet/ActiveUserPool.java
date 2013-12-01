package com.wilddynamos.bookfinderserver.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wilddynamos.bookfinderserver.model.User;

public abstract class ActiveUserPool {

	public static Map<String, User> session2user = new ConcurrentHashMap<String, User>();

	public static Map<Integer, List<Integer>> userId2bookIds = new HashMap<Integer, List<Integer>>();

}
