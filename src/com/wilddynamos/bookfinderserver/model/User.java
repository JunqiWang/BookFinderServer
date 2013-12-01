package com.wilddynamos.bookfinderserver.model;

/**
 * Model User
 * 
 * @author JunqiWang
 * 
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = -2908629024723879783L;

	private String email;

	private String password;

	private String name;

	private Boolean gender;

	private String campus;

	private String contact;

	private String address;

	private String photoPath;

	public User() {
		super();
	}

	public User(String email, String name, String password) {
		super();

		this.email = email;
		this.name = name;
		this.password = password;
	}

	public User(Integer id, String email, String password, String name,
			Boolean gender, String campus, String contact, String address,
			String photoPath) {
		super();

		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.campus = campus;
		this.contact = contact;
		this.address = address;
		this.photoPath = photoPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

}