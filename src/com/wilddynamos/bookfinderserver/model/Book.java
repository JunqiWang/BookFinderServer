package com.wilddynamos.bookfinderserver.model;

import java.util.Date;

/**
 * Model Book
 * 
 * @author JunqiWang
 * 
 */
public class Book extends BaseEntity {

	private static final long serialVersionUID = 1766341277163675267L;

	private String name;

	private Integer price;

	private Boolean per;

	private Integer availableTime;

	private Integer likes;

	private Boolean sOrR;

	private Boolean status;

	private String description;

	private Date postTime;

	private String coverPath;

	private Integer ownerId;

	private User owner;

	public Book() {
		super();
	}

	public Book(String name, Integer price, Boolean per, Integer availableTime,
			Boolean sOrR, String description, Integer ownerId) {
		super();

		this.name = name;
		this.price = price;
		this.per = per;
		this.availableTime = availableTime;
		this.sOrR = sOrR;
		this.description = description;
		this.ownerId = ownerId;
	}

	public Book(Integer id, String name, Integer price, Boolean per,
			Integer availableTime, Integer likes, Boolean sOrR, Boolean status,
			String description, Date postTime, String coverPath, Integer ownerId) {
		super();

		this.id = id;
		this.name = name;
		this.price = price;
		this.per = per;
		this.availableTime = availableTime;
		this.likes = likes;
		this.sOrR = sOrR;
		this.status = status;
		this.description = description;
		this.postTime = postTime;
		this.coverPath = coverPath;
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getPer() {
		return per;
	}

	public void setPer(Boolean per) {
		this.per = per;
	}

	public Integer getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(Integer availableTime) {
		this.availableTime = availableTime;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Boolean getsOrR() {
		return sOrR;
	}

	public void setsOrR(Boolean sOrR) {
		this.sOrR = sOrR;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
