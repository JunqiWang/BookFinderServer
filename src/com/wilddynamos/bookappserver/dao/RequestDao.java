package com.wilddynamos.bookappserver.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wilddynamos.bookappserver.model.Request;

public class RequestDao extends BaseDao<Request> {

	private static final Integer DEFAULT_REQUEST_PAGESIZE = 10;

	public RequestDao() {
		super();
	}

	public int add(Request request) {
		try {
			return stmt
					.executeUpdate("INSERT INTO request(id, message, status, "
							+ "request_time, book_id, requester_id)"
							+ " VALUES(null, "
							+ (request.getMessage() == null ? null : "'"
									+ request.getMessage() + "'")
							+ ", "
							+ null
							+ ", '"
							+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
									.format(request.getRequestTime()) + "', "
							+ request.getBookId() + ", "
							+ request.getRequesterId() + ");");

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int removeById(Integer id) {
		try {
			return stmt.executeUpdate("DELETE FROM request WHERE id='" + id
					+ "';");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int removeByProp(String name, String value, String order,
			Integer limit) {
		try {
			return stmt.executeUpdate("DELETE FROM request WHERE " + name
					+ " = '" + value + "' ORDER BY " + order + " LIMIT "
					+ limit + ";");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int update(Request request) {
		try {
			return stmt.executeUpdate("UPDATE request SET " + "status = "
					+ request.getStatus() + " " + "WHERE id = "
					+ request.getId() + ";");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int addOrUpdate(Request request) {
		if (request.getId() == null)
			return this.add(request);
		else
			return this.update(request);
	}

	public List<Request> findAll(String order, Integer currentPage,
			Integer pageSize) {
		List<Request> requests = new ArrayList<Request>();
		try {
			pageSize = (pageSize == null ? DEFAULT_REQUEST_PAGESIZE : pageSize);
			ResultSet rs = stmt.executeQuery("SELECT * FROM request ORDER BY "
					+ order + " LIMIT "
					+ (currentPage == null ? 0 : (currentPage - 1) * pageSize)
					+ ", " + pageSize + ";");

			while (rs.next())
				requests.add(new Request(rs.getInt(1), rs.getString(2), rs
						.getObject(3) == null ? null : rs.getBoolean(3), rs
						.getDate(4), rs.getInt(5), rs.getInt(6)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return requests;
	}

	public List<Request> findByProp(String name, String value, String order,
			Integer currentPage, Integer pageSize, int condition) {

		List<Request> requests = new ArrayList<Request>();
		try {
			String sql = "SELECT * FROM request WHERE " + name;
			switch (condition) {
			case 1:
				sql += " = '" + value + "'";
				break;
			case 2:
				sql += " like '%" + value + "%'";
				break;
			case 3:
				sql += " in " + value + "";
				break;
			case 4:
				sql += " < '" + value + "'";
				break;
			case 5:
				sql += " <= '" + value + "'";
				break;
			case 6:
				sql += " > '" + value + "'";
				break;
			case 7:
				sql += " >= '" + value + "'";
				break;
			default:
				sql += " = '" + value + "'";
			}
			pageSize = (pageSize == null ? DEFAULT_REQUEST_PAGESIZE : pageSize);

			sql += " ORDER BY " + order + " LIMIT "
					+ (currentPage == null ? 0 : (currentPage - 1) * pageSize)
					+ ", " + pageSize + ";";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println(rs.getBoolean(3) + "," + rs.getInt(1));
				requests.add(new Request(rs.getInt(1), rs.getString(2), (rs
						.getObject(3) == null ? null : rs.getBoolean(3)), rs
						.getDate(4), rs.getInt(5), rs.getInt(6)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requests;
	}

	public Request findByBookAndRequester(String bookId, String requesterId) {

		Request request = null;
		try {
			String sql = "SELECT * FROM request WHERE book_id = " + bookId
					+ " AND requester_id = '" + requesterId + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				request = new Request(rs.getInt(1), rs.getString(2),
						rs.getObject(3) == null ? null : rs.getBoolean(3),
						rs.getDate(4), rs.getInt(5), rs.getInt(6));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}
}