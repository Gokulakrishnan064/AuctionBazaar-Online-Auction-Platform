package com.auction.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.auction.app.entity.AuctionEntity;

import jakarta.transaction.Transactional;


public interface AuctionRepo extends JpaRepository<AuctionEntity, Integer> {
	@Query("SELECT u FROM AuctionEntity u WHERE u.user_id=:user_id")
	List<AuctionEntity> findByUserId(int user_id);
	
	@Modifying
    @Transactional
    @Query("UPDATE AuctionEntity a SET a.currentPrice = :currentPrice WHERE a.id = :auctionId")
    int updateCurrentPrice(int auctionId, String currentPrice);
}
