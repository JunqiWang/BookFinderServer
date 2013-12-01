package com.wilddynamos.bookfinderserver.service;

import java.util.List;

import com.wilddynamos.bookfinderserver.dao.BaseDao;

public abstract class BaseManager<E> {

	protected abstract BaseDao<E> getEntityDao();

	protected BaseManager() {
	}

	public void close() {
		getEntityDao().close();
	}

	public int add(E baseEntity) {
		return getEntityDao().add(baseEntity);
	}

	public int removeById(Integer id) {
		return getEntityDao().removeById(id);
	}

	public int removeByProp(String name, String value, String order, int limit) {
		return getEntityDao().removeByProp(name, value, order, limit);
	}

	public int update(E baseEntity) {
		return getEntityDao().update(baseEntity);
	}

	public int addOrUpdate(E baseEntity) {
		return getEntityDao().addOrUpdate(baseEntity);
	}

	public List<E> findAll(String order, Integer currentPage,
			Integer pageSize) {
		return getEntityDao().findAll(order, currentPage, pageSize);
	}

	public List<E> findByProp(String name, String value, String order,
			Integer currentPage, Integer pageSize, int condition) {

		return getEntityDao().findByProp(name, value, order, currentPage,
				pageSize, condition);
	}
}
