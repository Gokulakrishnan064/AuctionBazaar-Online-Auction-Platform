package com.example.demo.model;

import lombok.Data;

@Data
public class Update_user {
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String password;
	private String confirmPassword;
}