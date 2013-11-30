package com.wilddynamos.bookappserver.model;

import java.util.Date;

public class Request extends BaseEntity {

	private static final long serialVersionUID = -4171638851580115767L;

	private String message;

	private Boolean status;

	private Date requestTime;

	private Integer bookId;

	private Book book;

	private Integer requesterId;

	private User requester;

	public Request() {
		super();
	}

	public Request(String message, Date requestTime, Integer bookId,
			Integer requesterId) {
		super();
		
		this.message = message;
		this.requestTime = requestTime;
		this.bookId = bookId;
		this.requesterId = requesterId;
	}

	public Request(Integer id, String message, Boolean status,
			Date requestTime, Integer bookId, Integer requesterId) {
		super();
		
		this.id = id;
		this.message = message;
		this.status = status;
		this.requestTime = requestTime;
		this.bookId = bookId;
		this.requesterId = requesterId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Integer requesterId) {
		this.requesterId = requesterId;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}
}
