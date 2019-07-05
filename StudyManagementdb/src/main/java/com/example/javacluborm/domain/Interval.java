package com.example.javacluborm.domain;

import org.hibernate.annotations.Proxy;

@Proxy(lazy=false)
public class Interval {
	private Integer id;
	private Integer interval;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
}
