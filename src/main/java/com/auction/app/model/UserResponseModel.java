package com.auction.app.model;

import com.auction.app.entity.UserEntity;
import lombok.Data;

@Data
public class UserResponseModel extends ResponseModel{
	private UserEntity user;
}
