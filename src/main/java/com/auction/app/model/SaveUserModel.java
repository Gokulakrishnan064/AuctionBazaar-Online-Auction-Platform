package com.auction.app.model;

import lombok.Data;

@Data
public class SaveUserModel {
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
}
