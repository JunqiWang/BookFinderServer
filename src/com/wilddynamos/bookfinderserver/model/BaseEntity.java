package com.wilddynamos.bookfinderserver.model;

import java.io.Serializable;

/**
 * Base entity model
 * 
 * @author JunqiWang
 * 
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6747784454357654535L;

	/**
	 * All entities have an id
	 */
	protected Integer id;

	protected BaseEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
