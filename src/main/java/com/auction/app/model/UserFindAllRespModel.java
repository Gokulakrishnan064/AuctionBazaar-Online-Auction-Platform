package com.auction.app.model;

import java.util.List;

import com.auction.app.entity.UserEntity;

import lombok.Data;

@Data
public class UserFindAllRespModel extends ResponseModel {
	private List<UserEntity> allUsers;
}
