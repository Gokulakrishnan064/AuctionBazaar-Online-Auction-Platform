package com.example.demo.model;

import com.example.demo.entity.Userentity;

public class User_response_model extends ResponceModel {
	private Userentity user;

	public Userentity getUser() {
		return user;
	}

	public void setUser(Userentity user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "User_response_model [user=" + user + "]";
	}
}
