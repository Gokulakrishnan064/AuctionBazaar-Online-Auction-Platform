package com.auction.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auction.app.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer>{
	@Query("SELECT u FROM UserEntity u WHERE u.email=:email")
	UserEntity findByEmail(String email);
}
