package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Userentity;

public interface Userrepo  extends JpaRepository<Userentity, Integer>{
	@Query("SELECT u FROM Userentity u WHERE u.emailid=:email")
	Userentity findByEmail(String email);

}
