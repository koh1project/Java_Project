package com.example.javacluborm.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Login {
	private Integer id;

	@NotNull
	@NotBlank
	private String pass;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
}
