package com.auction.app.model;

import lombok.Data;

@Data
public class UpdateUserModel {
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String password;
	private String confirmPassword;
}
