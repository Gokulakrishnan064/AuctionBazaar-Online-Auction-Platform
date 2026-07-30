package com.auction.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auction.app.entity.AuctionEntity;
import com.auction.app.entity.BidsEntity;
import com.auction.app.repository.BidsRepo;

@Service
public class BidsServiceImpl {
	@Autowired
	private BidsRepo bidsRepo;
	@Autowired
	private AuctionServiceImpl auctionService;
	
	public BidsEntity saveRepo(BidsEntity bids) {
		AuctionEntity auction = auctionService.getAuctionById(bids.getAuctionId());
		if (auction != null && auction.getEndDateTime() != null) {
			if (auction.getEndDateTime().before(new Date())) {
				return null; // Reject bid if auction has ended
			}
		}
		int upd = auctionService.updateAmount(bids.getAuctionId(), bids.getAmount());
		if (upd != 0) return bidsRepo.save(bids);
		return null;
	}
	
	public BidsEntity readRecentHighestBid(int auctionId) {
		return bidsRepo.findHighestBidByAuctionId(auctionId) != null ? bidsRepo.findHighestBidByAuctionId(auctionId) : null;
	}

	public List<BidsEntity> findAllBidsByAuctionId(int auctionId) {
		return bidsRepo.findAllBidsByAuctionId(auctionId);
	}

	public List<BidsEntity> findBidsByUserId(int userID) {
		return bidsRepo.findByUserId(userID);
	}
	
	public BidsEntity findById(int Id) {
		Optional<BidsEntity> res = bidsRepo.findById(Id);
		return res.isEmpty() ? null : res.get();
	}
}
