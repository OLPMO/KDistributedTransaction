package com.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer USE_ID;

	@Column
	private String USE_NAME;
	
	@Column
	private Integer USE_AGE;

	public Integer getUSE_ID() {
		return USE_ID;
	}

	public void setUSE_ID(Integer uSE_ID) {
		USE_ID = uSE_ID;
	}

	public String getUSE_NAME() {
		return USE_NAME;
	}

	public void setUSE_NAME(String uSE_NAME) {
		USE_NAME = uSE_NAME;
	}

	public Integer getUSE_AGE() {
		return USE_AGE;
	}

	public void setUSE_AGE(Integer uSE_AGE) {
		USE_AGE = uSE_AGE;
	}
}
