package com.example.javacluborm.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class User {

	private Integer id;

	@NotNull
	@NotBlank
	private String loginNameId;

	@NotNull
	private Login login;

	@NotNull
	@NotBlank
	private String name;

	private String kana;
	private String mailAddress;
	private UserType userType;
	private Date created;

	public String getKana() {
		return kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}


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
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getLoginNameId() {
		return loginNameId;
	}
	public void setLoginNameId(String loginNameId) {
		this.loginNameId = loginNameId;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}


}
