package com.auction.app.model;

import lombok.Data;

@Data
public class UserAuthRequestModel{
	private String email;
	private String password;
}
