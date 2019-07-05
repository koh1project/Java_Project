package com.example.javacluborm.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Word {
	private Integer id;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@NotBlank
	private String detail;

	private Subject subject;
	private User user;
	private Date created;
	private Integer compulsory;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getCompulsory() {
		return compulsory;
	}
	public void setCompulsory(Integer compulsory) {
		this.compulsory = compulsory;
	}


}
