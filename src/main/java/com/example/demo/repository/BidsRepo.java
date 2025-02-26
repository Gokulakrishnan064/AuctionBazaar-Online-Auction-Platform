package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.BidsEntity;



public interface BidsRepo extends JpaRepository<BidsEntity, Integer>{
	@Query("SELECT u FROM BidsEntity u ORDER BY u.amount DESC, u.createdAt DESC LIMIT 1")
    BidsEntity findHighestBid();
	@Query("SELECT u FROM BidsEntity u WHERE u.userId=:userId")
	List<BidsEntity> findByUserId(int userId);
	
}