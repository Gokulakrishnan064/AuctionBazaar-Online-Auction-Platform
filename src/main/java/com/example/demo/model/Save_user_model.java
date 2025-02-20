package com.example.demo.model;

import lombok.Data;

@Data
public class Save_user_model {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private String confirmPassword;
}
