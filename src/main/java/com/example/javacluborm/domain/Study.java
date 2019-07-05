package com.example.javacluborm.domain;

import java.util.Date;


public class Study {

	private Integer id;
	private User user;
	private Word word;
	private Interval nextInterval;
	private Date lastStudyDate;
	private Date nextStudyDate;
	private Integer achievement;
	private Date created;
	private Integer priority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Date getLastStudyDate() {
		return lastStudyDate;
	}

	public void setLastStudyDate(Date lastStudyDate) {
		this.lastStudyDate = lastStudyDate;
	}

	public Date getNextStudyDate() {
		return nextStudyDate;
	}

	public void setNextStudyDate(Date nextStudyDate) {
		this.nextStudyDate = nextStudyDate;
	}

	public Integer getAchievement() {
		return achievement;
	}

	public void setAchievement(Integer achievement) {
		this.achievement = achievement;
	}

	public Interval getNextInterval() {
		return nextInterval;
	}

	public void setNextInterval(Interval nextInterval) {
		this.nextInterval = nextInterval;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
