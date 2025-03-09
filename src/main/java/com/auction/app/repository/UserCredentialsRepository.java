package com.auction.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.auction.app.entity.UserCredentialsEntity;


public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity,Integer>{
	@Query("SELECT u FROM UserCredentialsEntity u WHERE u.email=:email")
	UserCredentialsEntity findByEmail(String email);
	
	@Query("SELECT u FROM UserCredentialsEntity u WHERE u.user_Id=:user_Id")
	UserCredentialsEntity findByuserId(int user_Id);
}
