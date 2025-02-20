package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.AuctionEntity;

public interface AuctionRepo extends JpaRepository<AuctionEntity, Integer> {
	@Query("SELECT u FROM AuctionEntity u WHERE u.user_id=:user_id")
	List<AuctionEntity> findByUserId(int user_id);
}