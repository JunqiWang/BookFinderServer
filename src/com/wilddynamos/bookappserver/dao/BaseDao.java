package com.wilddynamos.bookappserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDao<E> {

	public static final String DB_ADDRESS = "jdbc:mysql://127.0.0.1:3306/book_app";

	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static final String USER_NAME = "root";

	public static final String PASSWORD = null;

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

	protected Connection conn;

	protected Statement stmt;

	public BaseDao() {
		try {
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DB_ADDRESS, USER_NAME,
					PASSWORD);
			this.stmt = this.conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public abstract int add(E entity);

	public abstract int removeById(Integer id);

	public abstract int removeByProp(String name, String value, String order,
			Integer limit);

	public abstract int update(E entity);

	public abstract int addOrUpdate(E entity);

	public abstract List<E> findAll(String order, Integer currentPage,
			Integer pageSize);

	public abstract List<E> findByProp(String name, String value, String order,
			Integer currentPage, Integer pageSize, int condition);
}
