package com.auction.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auction.app.entity.BidsEntity;

public interface BidsRepo extends JpaRepository<BidsEntity, Integer>{
	@Query("SELECT u FROM BidsEntity u WHERE u.userId=:userId")
	List<BidsEntity> findByUserId(int userId);
	@Query("SELECT u FROM BidsEntity u WHERE u.auctionId = :auctionId ORDER BY u.amount DESC, u.createdAt DESC")
	BidsEntity findHighestBidByAuctionId(int auctionId);

	
}
